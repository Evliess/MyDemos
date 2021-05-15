import requests
import json
import time
from pyquery import PyQuery as pq
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities
from selenium.common.exceptions import WebDriverException
from datetime import datetime
from requests.exceptions import HTTPError


__city = 'sh'
__years = 5
__geckodriverPath = r'./drivers//geckodriver.exe'
__url = 'https://fangjia.fang.com/fangjia/suggestion/transfer/' + __city


def __splitAreaCode(url):
    code = url.replace('https://fangjia.fang.com/process/sh/','').replace('.htm', '')
    return code

# return areaName
def __getAreaCode(areaName):
    try:
        response = requests.get(__url, params={
            'projname': areaName, 'strnewcode': '', 'strcity': '', 'boolnewsearch': 'True', '_csrf': '4b67zSz1-y-qYKvkBb19Wa26KtX7eiwIDPSY'
        })
        code = response.status_code
        if(code == 200):
            return __splitAreaCode(response.url)
        else:
            return ''
    except HTTPError as http_err:
        print(f'HTTP error occurred: {http_err}')
    except Exception as err:
        print(f'Other error occurred: {err}')
    else:
        print('Success!')

# return price array

def __getAreaPrice(areaCode):
    try:
        response = requests.get('https://fangjia.fang.com/fangjia/common/ajaxdetailtrenddata/' + __city, params={
            'dataType': 'proj', 'projcode': areaCode, 'year': __years
        })
        code = response.status_code
        if(code == 200):
            return response.text
        else:
            return ''
    except HTTPError as http_err:
        print(f'HTTP error occurred: {http_err}')
    except Exception as err:
        print(f'Other error occurred: {err}')
    else:
        print('Success!')

def __getPriceByAreaName(areaName):
    areaCode = __getAreaCode(areaName)
    price = __getAreaPrice(areaCode)
    result = '"' + areaName + '" : ' + price
    return result

def getAvgPrice(areaNames):
    price = ''
    for area in areaNames:
        price = price + __getPriceByAreaName(area) + ','
    price = price[:-1]
    content = '{' + price + '}'
    dataAvg = json.loads(content)
    return dataAvg

def __getChenJiaoFromHtml(html, result, name, value):
    d = pq(html)
    if(result['count'] == 0):
        th = d('div.dealSent table tr th').items()
        result['labels'] = []
        result['items'] = []
        for i in th:
            result['labels'].append({'title' : str(i.text())})
        result['labels'].insert(0, {'title' : '小区名字'})
    td = d('div.dealSent table tr td').items()
    split = 0
    for j in td:
        if(split == 0) :
            item = []
        item.append(str(j.text().replace('\n', ' ')))
        if (split > 0 and split % 5 == 0):
            item.insert(0, value)
            result['items'].append(item)
            split = 0
        else:
            split += 1
def __getHtmlSource(driver, url, retry):
    try:
        print('{} times retry to open url {}'.format(retry, url))
        driver.get(url)
    except WebDriverException:
        if (retry > 0):
            time.sleep(3)
            retry = retry - 1
            __getHtmlSource(driver, url, retry)
        if (retry == 0):
            driver.close()



def __getChengJiaoByName(areaNames, driver, result):
    __retry = 5
    for name in areaNames:
        url = "https://" + name + ".fang.com/chengjiao/"
        try:
            __getHtmlSource(driver, url, __retry)
            __getChenJiaoFromHtml(driver.page_source, result, name, areaNames[name])
            element = driver.find_element_by_id("ctl00_hlk_next")
            while(element):
                element.click()
                result['count'] = result['count'] + 1
                __getChenJiaoFromHtml(driver.page_source, result, name, areaNames[name])
                element = driver.find_element_by_id("ctl00_hlk_next")
        except NoSuchElementException:
            print('There is no more data! {}, total: {}'.format(driver.current_url, len(result['items'])))
        finally:
            pass
        result['count'] = result['count'] + 1

def __initLocalSeleniumDriver():
    return webdriver.Firefox(executable_path=__geckodriverPath)

def __initRemoteSeleniumDriver():
    capabilities = DesiredCapabilities.CHROME
    capabilities['acceptSslCerts'] = True
    capabilities['acceptInsecureCerts'] = True
    return webdriver.Remote(command_executor='http://19.130.161.12:4444/wd/hub', 
    desired_capabilities=capabilities, 
    browser_profile=None, 
    proxy=None, keep_alive=False, file_detector=None, options=None)

def getChengjiao(areaNames):
    result = {}
    result['count'] = 0
    try:
        driver = __initLocalSeleniumDriver()
        __getChengJiaoByName(areaNames, driver, result)
    finally:
        driver.close()
    return json.dumps(result)

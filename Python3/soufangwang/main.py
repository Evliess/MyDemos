from selenium import webdriver
from fangtianxia import getAvgPrice
from fangtianxia import getChengjiao
from fileutil import read
from fileutil import write
import json
import os
from datetimeutil import format


geckodriverPath = r'./drivers/geckodriver.exe'

def __createIndexHtml(jsonData, dataChengjiao):
    colors = ['#EF5350', '#FFA726', '#FFEA00', '#66BB6A', '#29B6F6', '#8D6E63', '#7E57C2', '#FF5252', '#EEFF41', '#283593', '#CE93D8']
    dataAvg = {}
    datasets = []
    labels = []
    count = 0
    for key in jsonData:
        dataset = {}
        data = []
        dataset['label'] = key
        dataset['data'] = data
        dataset['backgroundColor'] = colors[count]
        dataset['borderColor'] = colors[count]
        dataset['fill'] = False
        for value in jsonData[key]:
            dataTime = value[0]
            price = value[1]
            data.append(str(price))
            if count==0 :
                labels.append(str(format(dataTime)))
        count+=1
        datasets.append(dataset)
    dataAvg['datasets'] = datasets
    dataAvg['labels'] = labels
    htmlContent = read('./frontend/vm/index.vm')
    htmlContent = htmlContent.replace('${dataAvg}', json.dumps(dataAvg))
    htmlContent = htmlContent.replace('${dataChengjiao}', dataChengjiao)
    write('./frontend/index.html', htmlContent)

def getInfo():
    areas = ['华沙一村', '园西一居', '临丰小区', '青夏小区', '上浦小区', '临河小区']
    dataAvg = getAvgPrice(areas)
    areaNames = {
        'shangpuxiaoqu' : '上浦小区',
        'huashayicun' : '华沙一村',
        'yuanxierju' : '园西一居',
        'linfengxiaoqu' : '临丰小区',
        'linhexiaoqu' : '临河小区',
        'qingxiaxiaoqu2' : '青夏小区'
        
    }
    chengJiaoResult = getChengjiao(areaNames)
    __createIndexHtml(dataAvg, chengJiaoResult)
    driver = webdriver.Firefox(executable_path=geckodriverPath)
    driver.get("file:///" + os.getcwd() + '/frontend/index.html')

def main():
    getInfo()


if __name__ == '__main__':
    main()
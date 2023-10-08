from fangtianxia import getChengjiao
from fileutil import read
import json
import time
import codecs
from datetimeutil import format


from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.action_chains import ActionChains

chrome_options = webdriver.ChromeOptions()
chrome_options.add_argument('--proxy-server=http://127.0.0.1:7890') 
chrome_options.add_experimental_option('detach', True);
chrome_options.add_argument("--headless=True");
driver = webdriver.Chrome(chrome_options)


_xiaoshuo = 'https://book.xbookcn.net/search/label/%E8%B0%83%E6%95%99%E5%A5%B3%E5%8F%8B?max-results=1000'
_name = "tiaojiaonvyou.txt"



links=[]
driver.get(_xiaoshuo)

for ele in driver.find_elements(By.CSS_SELECTOR, "div.post-outer h3.post-title.entry-title a"):
  links.append(ele.get_attribute("href"))
print(len(links))

for link in links:
   driver.implicitly_wait(3)
   driver.get(link)
   ele = driver.find_element(By.CSS_SELECTOR, "div.post-outer")
   with codecs.open(_name, "a", "utf-8") as myfile:
    myfile.write("\n")
    myfile.write(ele.text)

driver.quit()

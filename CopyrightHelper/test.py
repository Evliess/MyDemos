import PySimpleGUI as sg 
import logging
import logging_config
import re

import os

from fileUtil import read

logger = logging.getLogger('test.py')
# logger.debug('hello')
# logger = logging.getLogger('copyrightHelper')
# logger.debug('world')


content = read('./temp')

pattern = '\\/\\*(\\s|.)*?\\*\\/|#{2,}(\\s|.)*?\\*?#{2,}|<!--(\\s|.)*?\\*?>'

result = re.match(pattern, content, flags=0)

logger.debug(result.group())


file = 'fileutil'

logger.debug(os.path.splitext(file)[-1])


logger.debug('.java'.find('Java') > 0)





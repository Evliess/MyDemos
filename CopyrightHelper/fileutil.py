import os
import re
import logging

import logging_config

logger = logging.getLogger('fileutil.py')

def write(filename, content):
  try:
    fo = open(filename, 'w+', encoding="utf-8")
    fo.write(content)
    fo.close()
  except Exception as ex:
    logger.error('Write %s error! %s', filename, ex)

def read(filename):
  try: 
    fo = open(filename, 'r+', encoding="utf-8")
    content = fo.read()
    fo.close()
    return content
  except Exception as ex:
    logger.error('Read %s error! %s', filename, ex)

## Using '(C) Copyright' to check existing copyright header by default.
def removeOldCopyright(content):
  pattern = '\\/\\*(\\s|.)*?\\*\\/|#{2,}(\\s|.)*?\\*?#{2,}|<!--(\\s|.)*?\\*?>'
  result = re.match(pattern, content, flags=0)
  if (result != None):
    oldCopyright = result.group()
    copyrightMarker = '(C) Copyright'
    if (oldCopyright.find(copyrightMarker) !=-1):
      content = content.replace(oldCopyright, '')
    else:
      pass
  content = '\n' + content.lstrip()
  return content

def getSourceFiles(path):
  sourceFiles = []
  for root, directories, files in os.walk(path, topdown=False):
    for name in files:
      filePath = os.path.join(root, name)
      sourceFiles.append(filePath)
    for name in directories:
      pass
  return sourceFiles

def appendCopyrightHeader(window, sourceFiles, copyrightContent):
  progressbar = window['progressbar']
  messageText = window['message']
  totalFiles = len(sourceFiles)
  i = 1
  try:
    for file in sourceFiles:
      logger.info('Update file: %s', file)
      originalContent = read(file)
      copyrightContent = copyrightContent.strip() + '\n'
      originalContent = removeOldCopyright(originalContent)
      content = copyrightContent + originalContent
      i += 1
      messageText.update(file)
      progressbar.update(i, totalFiles)
      write(file, content)
    messageText.update('Done!')
  except Exception as ex:
    messageText.update('Error! Please see message.log for more details.')
    logger.error('Add/Update copyright header to %s error! %s', file, ex)

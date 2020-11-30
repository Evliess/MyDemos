import os
import re
import logging

import logging_config


logger = logging.getLogger('copyrightHelper')

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

def appendCopyrightHeader(window, sourceFiles, values):
  progressbar = window['progressbar']
  messageText = window['message']
  copyrightContent = values['content']
  selectLanguage = values['language']
  selectLanguage = selectLanguage.lower()
  totalFiles = len(sourceFiles)
  i = 0
  try:
    progressbar.update(i, totalFiles)
    for filePath in sourceFiles:
      if (getFileType(filePath).find(selectLanguage) != -1):
        updateCopyright(filePath, copyrightContent)
        messageText.update(filePath)
        progressbar.update(i, totalFiles)
        i += 1
    messageText.update('Done!')
    progressbar.update(totalFiles)
  except Exception as ex:
    messageText.update('Error! Please see message.log for more details.')
    logger.error('Add/Update copyright header to %s error! %s', filePath, ex)

def getFileType(filePath):
  return os.path.splitext(filePath)[-1]

def updateCopyright(filePath, copyrightContent):
  try:
    logger.info('Update file: %s', filePath)
    originalContent = read(filePath)
    copyrightContent = copyrightContent.strip() + '\n'
    originalContent = removeOldCopyright(originalContent)
    content = copyrightContent + originalContent
    write(filePath, content)
  except:
    raise   
import PySimpleGUI as sg
from fileUtil import read
from fileUtil import appendCopyrightHeader
from fileUtil import getSourceFiles

def formattingComboxListener(window, values):
    if values['language'] == "Java":
      window['content'].update(read('./copyrights/java-template'))
    if values['language'] == "Javascript":
      window['content'].update(read('./copyrights/js-template'))
    if values['language'] == "Shell":
      window['content'].update(read('./copyrights/shell-template'))
    if values['language'] == "Xml":
      window['content'].update(read('./copyrights/xml-template'))

def applyBtnListener(window, values):
  appendCopyrightHeader(window, getSourceFiles(values['source']), values)

def setDefaultLanguage(window):
  window['language'].update('Java')
  window['content'].update(read('./copyrights/java-template'))

def handleWidgetEvents(window):
  setDefaultLanguage(window)
  while True:
    event, values = window.read()
    if event == 'apply':
      applyBtnListener(window, values)
    if event == 'language':
      formattingComboxListener(window, values)
    if event in (sg.WIN_CLOSED, 'Exit'):
        break
  window.close()

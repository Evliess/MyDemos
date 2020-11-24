import PySimpleGUI as sg
from fileutil import read
from fileutil import appendCopyrightHeader
from fileutil import getSourceFiles

supportedLanguages = ('Java', 'Javascript', 'Shell', 'Xml')

def initUI():
  background_color = '#1d3649'
  button_backgroud_color = '#b4e051'
  progress_bar_color = ['#b4e051', '#fff']
  layout = [
    [sg.Text('Apply to', size=(8, 1), background_color=background_color), 
    sg.Input(size=(65, 1), key="source"), 
    sg.FolderBrowse(size=(6, 1), button_color=['#000', button_backgroud_color])],
    [sg.Text('Formatting', size=(8, 1), background_color=background_color), 
    sg.InputCombo(values=supportedLanguages, size=(63, 3), key='language', enable_events=True), 
    sg.Button('Apply', size=(6, 1), key='apply', button_color=['#000', button_backgroud_color])],
    [sg.Text('Copyright Content', size=(60, 1), background_color=background_color)],
    [sg.Multiline(size=(82, 15), key='content')],
    [sg.Text('', size=(60, 2), key='message', background_color=background_color)],
    [sg.ProgressBar(100, orientation='h', size=(54, 3), key='progressbar', bar_color=progress_bar_color)]
  ]
  window = sg.Window('Copyright-Header Tool', layout, no_titlebar=False, finalize=True, background_color=background_color)
  handleWidgetEvents(window)


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
  appendCopyrightHeader(window, getSourceFiles(values['source']), values['content'])

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

import PySimpleGUI as sg

from interfaceUtil import handleWidgetEvents

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
    [sg.ProgressBar(100, orientation='h', size=(46, 3), key='progressbar', bar_color=progress_bar_color)]
  ]
  window = sg.Window('Copyright-Header Tool', layout, no_titlebar=False, finalize=True, background_color=background_color)
  handleWidgetEvents(window)

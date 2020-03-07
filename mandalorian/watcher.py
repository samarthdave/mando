import os, re
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler

class GenericHandler(FileSystemEventHandler):
  def __init__(self, handler, match_regex=None):
    self.handler = handler
    # match_regex handles file names as well as file types
    self.match_regex = match_regex
  
  def on_modified(self, event):
    # Don't call handler if source path doesn't match regex (check)
    if self.match_regex and not re.match(self.match_regex, event.src_path):
      return
    self.handler(event.src_path)

class Watcher(object):
  """ Provides an easy interface to watch files 

  * callback function signature: callback(<file_path>)

  """
  def __init__(self, start_observer=False):
    self.observer = Observer()
    if start_observer:
      self.observer.start()

  def watch_file(self, file_path, callback):
    # Get absolute path to file
    if not os.path.isabs(file_path):
      file_path = os.path.join(os.getcwd(), file_path)

    # Watchdog watches full directories for changes
    folder_path = os.path.dirname(file_path)
    self.observer.schedule(GenericHandler(callback, file_path), folder_path, recursive=False)
  
  def watch_folder(self, folder_path, callback, regex=None):
    if not os.path.isabs(folder_path):
      folder_path = os.path.join(os.getcwd(), folder_path)

    self.observer.schedule(GenericHandler(callback, regex), folder_path, recursive=False)

  def start_watcher(self):
    if self.observer.is_alive():
      return
    self.observer.start()

  def stop_watcher(self):
    self.observer.stop()

  def __del__(self):
    if self.observer.is_alive():
      self.observer.stop()
    # Wait for observer to stop before deleting watcher
    print("Stopping observer")
    self.observer.join()
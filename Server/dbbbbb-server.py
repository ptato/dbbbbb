from bottle import *
from pathlib import Path
from pprint import pprint
from environment import environment
import json, os


data_folder = environment['data_folder']
allowed_extensions = [ '.mp3', '.flac' ]
metadata_file = 'md.json'
whitelisted_users = { 'ptato': 'ptato' }

# note: pass authentication by sending an 'Authorization'
#       header with the value 'Basic user:pswd'
def check_auth(user, pswd):
    return user in whitelisted_users and pswd == whitelisted_users[user]

def updated_metadata_file():
    metadata = dict()
    metadata['root'] = []
    for fp in Path(data_folder).glob('**/*'):
        if any(( fp.suffix == ext for ext in allowed_extensions )):
            metadata['root'].append(str(fp))
    return metadata

@get('/static/<filepath:path>')
@auth_basic(check_auth)
def serve_static(filepath):
    if filepath == metadata_file:
        return updated_metadata_file()
    elif any(( filepath.endswith(ext) for ext in allowed_extensions )):
        return static_file(filepath, root=data_folder)
    else:
        abort(403, 'Disallowed file extension')

@get('favicon.ico')
def serve_favicon():
    return static_file('favicon.ico')

# todo: check https://github.com/nickbabcock/bottle-ssl

run(host=environment['host'], port=environment['port'], debug=True, reloader=True)

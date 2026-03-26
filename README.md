# sshTunnelManager


_Under Construction_


## Base Requirement

- graphical Java Application for managing SSH-(Double-)Tunnels
- only 1 instance allowed, if a second is started, show an error message
- Tray Icon
  - with menu: 
    - show
    - about
    - exit
- invent a meaningful icon reflecting the functionality (Tunnel), that is used as
  - window icon
  - taskbar icon
  - tray icon
- Following Model-View-Controller
- For data, following DAO/DTO/BO
- Class Structure: follow common best-practice

## Data File Requirements

- to be stored in folder "conf" of the application
- content of name fields must be unique 
- content of address fields must match regex pattern for an FQDN
- content of port fields must be numeric, between 1025 and 65534
- mappedPort: additional requirement
  - must be unique
  - for firstHost: 10000-19999
  - for secondHost: 20000-29999
  - offers dropdown with available (unused) ports
  - shall propose to the next unused
  - user might enter manually
  - for secondHost: if unused, propose same as for firstHost (first digit increased from 1 to 2 for sure)
  - error message and connection not saved if not in allowed range

## Data Structure 

- datacenters.json
  - available "Front-Channel-Servers" (reachable via SSH for opening a tunnel to other servers)
  - Fields:
    - name
    - address
    - port (must be numeric, between 1025 and 65534)

- configuration.json
  - Fields
    - Connection Parameters
      - ConnectTimout (default: 60000ms)
      - AliveTestTimeout (default: 20000ms)
      - AliveTestFrequency (default: 5000ms)
      - retryDelay (default: 3000ms)
    - UI
      - width
      - height
      - connectionList (default: true)
      - favoritesOnly (default: false)
      - closeToTray (default: false)
      - Connection-Colors
      - PP2 = #04D1D3
      - PRE2 = #04D1D3
      - PREPROD2 = #04D1D3
      - PP = #001FFF
      - PREPROD = #001FFF
      - PRE = #001FFF
      - UAT = #001FFF
      - PRD = #990000
      - PROD = #990000
      - STG2 = #997C09
      - STG3 = #665306
      - STG = #D1A90A
      - STAGING = #D1A90A
      - TEST = #D1A90A
      - SIT = #D1A90A 

- connectionTypes.json
  - name
  - port
  - isDoubleTunnel (default: true)

- credentials.json
  - user
  - password (encrypted)
  - keyfile (path to private key)
  - privkeyPassphrase (encrypted)
  - lastModified (date)

- tunnels.json
  - folders
    - name
    - expanded (boolean, default: true)
    - connections
      - name
      - connectionTypes (dropdown, source: connectionTypes.json)
      - datacenter (dropdown, source: datacenters.json)
      - firstHost
        - address
        - password
          - encrypted
          - optional, default: out of credentials
        - hostPort
        - mappedPort 
      - secondHost
        - address
        - mappedPort
        - password
          - encrypted
          - optional, default: out of credentials
      - isFavorite (default: false)
      - passwordOverride (default: false)

## Application

### First Startup

#### Requirements

- Screen, asking for defining a Master Password (2 times)
- if both match, next screen asking for credentials (see credentials.json)

#### Validations

- Master Password must be checked agains common best practice in password complexity
- given private key file must be in valid OpenSSH or Putty Private Key format
- given passphrase must allow loading the given private key file

#### Steps

- create Masterpassword
- start main application

### Following Startup

#### Requirements

- credentials.json exists and content makes sense
- else: First Start

#### Screen

  - Buttons: 
    - OK - validate password
      - try decrypting encrypted content of credentials.json
    - Cancel - exit app
    - Reset Masterpassword
      - delete credentials.json
      - fall-back to "First Start"

#### Steps

- ask for Masterpassword
- validate
- failed: error message, retry
- max retries: 3 - still failed: exit app
- valid password: start main app

### Main App

#### Requirements

- All editing screens are graphical Forms, not just "editors"

#### Menu

- Add
  - Folder (Folder editing Screen)
  - Connection (Connection editing Screen)
- Settings
  - Reload
    - reloads from files
  - Reset Master Password (same functionality as the related button in the starting screen)
  - Update Credentials (same screen as at first start when defining credentials)
  - Show Active Connections 
    - Checkbox
    - relates to connectionList
  - Show Favorites Only
    - Checkbox
    - relates to favoritesOnly
  - Close to Tray 
    - Checkbox
    - relates to closeToTray
  - Edit Data Centers (datacenters editing screen)
- Log
  - open Log file (opens the log in the OS default Editor for json files)
- Application
  - About (opens About-Screen with authoring and version information)
  - Hide (hide to tray, if closeToTray = true, else greyed out)
  - Exit

#### Screen

- left side
  - tree of folders and connections
  - depending on the status of "favoritesOnly", show all or just favorites
- right side
  - if connectionList = true
    - Active Connections
      - lists active connections
      - folder and connection name concatenated, separated by a dash surrounded by blanks
      - sorted alphabetically
  - else: hidden (connections use full window)
- Folders
  - sorted alphabetically
  - can be expanded or collapsed
  - right-click on folders brings menu:
    - edit
    - delete
- Connections
  - sorted alphabetically
  - have a connection indicator
    - connected: green
    - connected: blinking green
    - disconneced: red
    - when clicked: connect/disconnect
  - have another button after the connection name
    - showing the value of "secondHost/mappedPort"
    - click on the "port-button" pushes the port number to the clipboard
    - port buttons should be "aligned to the right" per folder so all of these are in one line
  - right-click menu:
    - Edit
    - Delete
    - Toggle Favorite
- Active Connections
  - any established connection should be listed here
  - Folder/Connection Name prefixed by the connection indicator (including disconnect functionality)

### Other Screens

#### Add/Edit Connection

- If opened for editing, all fields pre-filled with the values, found in existing data

##### Fields

- Folder
  - Drop Down providing existing folders
  - right of the drop-down: button to create a new folder
- Type
  - Drop Down providing existing connectionType names
- Connection Name
- Data Center 
  - Dropdown, providing existing datacenter names
- Favorite
  - Check Box
- Password Override
  - Checkbox
  - hides/unhides additional fields:
- First Tunnel Host
- First Tunnel Host Port (Optional, default 22)
- First Tunnel Mapped Port
- (First Tunnel Password)
- Hint: if connectionType's isDoubleTunnel is false, all "Second Tunnel" Fields shall be hidden.
- Second Tunnel Host
- Hint: Second Tunnel Host Port is not required as a field here as this information is taken from the connectionType
- Second Tunnel Mapped Port
- (Second Tunnel Password)

##### Buttons

- Save

#### Add/Edit Folder

- If opened for editing, all fields pre-filled with the values, found in existing data

##### Fields

- Name

##### Buttons

- Save

#### Create/Update Credentials

- If opened for editing, all fields pre-filled with the values, found in existing data
- Password/Passphrase hidden for sure

##### Fields

- Linux User Name
- Linux Password
- Last Password Change
  - initially not editable
  - right of it there shall be a button, that makes it editable
- Private Key File
  - right of the field, there shall be a button allowing the user to browse the file system
- Key Passphrase

##### Buttons

- Save

#### Edit Data Centers

- If opened for editing, all fields pre-filled with the values, found in existing data

##### Form

- is a list of datacenters
- each row shall contain a datacenter, Fields:
  - Check Box
  - Name
  - Host Name / IP (-> address)
  - SSH Port
- shall be sorted alphabetically

##### Buttons

- Delete
  - deletes all, that have the checkbox checked
- Add (openes Create Data Center screen)
  - adds one line with empty fields
- Save

## Validations

### Basics

- called, when before saving data
- pop up meaningful error message if violated
- violations shall be made visible in the form (text field border red)

### Rules

- Names shall be alphanumeric plus
  - underscore
  - dot
  - dash
  - slash
- Ports: numeric, between 1025 and 65534
- Addresses (host names): 
  - in connections: must per regex check be a valid host name, a valid fully qualified domain name or a valid IP
  - in data centers: must per regex check be a valid fully qualified domain name or a valid IP
- Dropdown must have a value chosen

## Other expecations

- meaningful logging (common best practice)
- as far as the SSH library allows
  - if connect fails due to auth-reject, don't retry! That may cause account locks
- SSH library will just understand OpenSSH Private Key Format
  - if Putty Private Key format is detected
    - convert it and store in a new file
    - adjust the path: point to new file
    - inform the user about the action and provide him with the path to new file
- connections should be "monitored"
  - AliveTestTimeout
  - AliveTestFrequency
- on connection loss, try reconnecting 3 times (retryDelay)
- Full integration into VS Code with Gradle
  - have run configurations
  - no VS Code Auto-Build (too slow)
    - have a gradle build script
    - which is added as run configuration
- Unit tests created where it makes sense
- meaningful Java Doc

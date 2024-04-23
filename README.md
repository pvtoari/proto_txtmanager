# Proto
W.I.P. Java-based simple CLI with navigating and text view-editing fuctions.

## How to run in headless mode

Since JNativeHook needs a X11 display to work, a solution can be done using noVNC, that means using a web to capture keyboard stuff.

This is possible with noVNC (opens new window) (a web-based VNC client) and Xtigervnc (opens new window) (a VNC server that supports X11). Here’s a script to set them up with Docker Compose. To use it, copy the following snippet into a new Bash terminal:

```
mkdir -p ~/setup-display && cd ~/setup-display
cat > docker-compose.yml <<EOF
services:
  display:
    image: ghcr.io/dtinth/xtigervnc-docker:main
    tmpfs: /tmp
    restart: always
    environment:
      VNC_GEOMETRY: 1440x900
    ports:
      - 127.0.0.1:5900:5900
      - 127.0.0.1:6000:6000
  novnc:
    image: geek1011/easy-novnc
    restart: always
    command: -a :5800 -h display --no-url-password
    ports:
      - 127.0.0.1:5800:5800
EOF
docker compose up -d
```

Once run, we need to tell our applications to connect to the server we started. To do that, set the DISPLAY environment variable to ``127.0.0.1:0``:

```export DISPLAY=127.0.0.1:0```

Once that’s done, keyboard-capturing functionalities will work on a headless mode environment by using a web interface.

Credits to [@dtinth](https://github.com/dtinth) for the noVNC guide.

## Requirements

When using functionalities where JNativeHook is involved, there might be some problems with missing packages, for example using Codespaces.

For Linux, using a Bash terminal, run the following commands:
```
apt-get install -y libxkbcommon-x11-0
apt install libxkbcommon-dev
apt install libxtst6
apt install libxtst-dev
```

Errors in other operating systems are not expected.
# burton

## Development Notes

You need three windows.  Two can be inside the IDE, but one must be native Terminal or iTerm.

### electron-dev

```
lein cljsbuild auto electron-dev
```

This builds (and re-builds) the Electron application itself.
This is not where you do most of your work, mostly (AFAIK) it exists to launch the UI.
 
Later there will be cross-window logic here, I suspect.


### figwheel

```
lein figwheel frontend-dev
```

This should be run in a Terminal window, to get the full REPL experience.

The REPL starts up and blocks, waiting for the UI to launch.

### electron

```
electron .
```

This launches Electron for the current directory. `package.json` defines that the main
file is `resources/main.js` (generated by the electron-dev step above).

`main.js` is generated from `main.cljs`, which loads the Electron window as
`resources/public/index.html`.  And that references JavaScript compiled from ClojureScript by figwheel.

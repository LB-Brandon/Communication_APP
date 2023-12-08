# Communication_APP

# Overview
Basic Communication: The app focuses on establishing fundamental communication protocols.

## What I learned
- Socket Server/Client
- Synchronous/Asynchronous Http communication using OkHttp
- Thread
    - runOnUiThread
- JSON parse using GSON

## Key Function
- Server runs on PC, Client runs on Android app
- Client makes a request asynchronously
- Client parses the response (JSON to Data class)

## Troubleshooting

#### Socket Communication in a Main Thread
Error: `android.os.NetworkOnMaininTreadException`  
Action: Ensured that socket communication functions within a separate thread, preventing potential issues in the main thread.  
Explanation: Network communication might need long time to finish, this can be block the UI(main) Thread.  

#### UI handing in Main Thread
Error: `android.view.ViewRootImpl$CalledFromWrongThreadException`: Only the original thread that created a view hierarchy can touch its views.  
Action: Avoided UI-related methods running in the main thread by utilizing the runOnUiThread scope.  

#### HTTP Communication with OkHttp
Error: `java.net.UnknownServiceException`: CLEARTEXT communication to 10.0.2.2 not permitted by network security policy  
Action: When communicating using HTTP, OkHttp requires the clear text option to be set to true, but this is not necessary for socket communication.

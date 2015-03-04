## Company Box Fun

Uses Java Box SDK (beta) to perform simple Box tasks.

Current operations

- Get file info
- Build folder content tree from any folder
- Store and read dev config locally

### Setup

1. Go to Box developer site
1. Create an Application (Must have globally unique name)
1. Generate a developer token
1. Rename the `res/example.local.config.json` file to `res/local.config.json`
1. Place the token in the config file
1. Then in IntelliJ mark the `res` folder as **Resource Root**


### Notes

- https://developers.box.com/detailed-error-messages/
- http://opensource.box.com/box-java-sdk/javadoc/com/box/sdk/BoxEvent.Type.html
- https://developers.box.com/docs/#events-get-events-in-an-enterprise

# alfred-api
#### Basic flowchart
![N|Solid](https://i.imgur.com/SzoMmjG.png)

#### Webhook (GITHUB)
> [POST] /wh-git-hub
```json
{
  "ref": "branch",
  "before": "~hash~",
  "after": "~hash~",
  "head_commit":
  {
    "message": "commit massage",
    "timestamp": "data time",
    "url": "commit url"
  },
  "repository":
  {
    "name": "application name to build",
    "full_name": "application name to build"
  },
  "sender":
  {
    "login": "sender login",
    "avatar_url": "avatar url"
  }
}

```
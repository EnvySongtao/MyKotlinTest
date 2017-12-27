package com.gst.mykotlintest.ui.mvptest

import com.google.gson.annotations.SerializedName

/**
 * author: GuoSongtao on 2017/12/7 10:56
 * email: 157010607@qq.com
 */
/**
 * {
"login": "envy",
"id": 241552,
"avatar_url": "https://avatars0.githubusercontent.com/u/241552?v=4",
"gravatar_id": "",
"url": "https://api.github.com/users/envy",
"html_url": "https://github.com/envy",
"followers_url": "https://api.github.com/users/envy/followers",
"following_url": "https://api.github.com/users/envy/following{/other_user}",
"gists_url": "https://api.github.com/users/envy/gists{/gist_id}",
"starred_url": "https://api.github.com/users/envy/starred{/owner}{/repo}",
"subscriptions_url": "https://api.github.com/users/envy/subscriptions",
"organizations_url": "https://api.github.com/users/envy/orgs",
"repos_url": "https://api.github.com/users/envy/repos",
"events_url": "https://api.github.com/users/envy/events{/privacy}",
"received_events_url": "https://api.github.com/users/envy/received_events",
"type": "User",
"site_admin": false,
"name": "Nico Weichbrodt",
"company": null,
"blog": "",
"location": "Germany",
"email": null,
"hireable": null,
"bio": null,
"public_repos": 8,
"public_gists": 4,
"followers": 8,
"following": 4,
"created_at": "2010-04-11T14:09:36Z",
"updated_at": "2017-11-23T14:14:07Z"
}
 */
class MVPUserBeen
constructor(@SerializedName("id") val id: Int,
            @SerializedName("name") var nameRepo: String,
            @SerializedName("login") var loginName: String,
            @SerializedName("location") var location: String,
            @SerializedName("language") var language: String) {

    constructor() : this(-1, "", "", "", "")
}
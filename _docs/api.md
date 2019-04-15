接口文档
------

>   `jersey-jetty-api-service-demo` 主要实现简单的 `OAuth` 登录，会话用户对页面的增删改查功能。

### 路由

| 方法       | 路由 或 URI        | 备注                      |
| :------- | :-------------- | :---------------------- |
| `post`   | `/api/login`    | 通过登录账号获取 access-token 。 |
| `get`    | `/api/pages`    | 获取当前用户所有页面，附带分页。        |
| `post`   | `/api/page`     | 创建一个新页面。                |
| `get`    | `/api/page/@id` | 获取指定 id 页面。             |
| `put`    | `/api/page/@id` | 更新指定 id 页面。             |
| `delete` | `/api/page/@id` | 删除指定 id 页面。             |

### API 状态码或错误码

| 状态码或错误码      | 备注                |
| :----------------- | :---------------- |
| 500/50000          | 失败或错误。            |
| 401 (Unauthorized) | access token 已过期。 |
| 403 (Forbidden)    | 非法或不正确的（登录）凭证。    |
| 404 (Not Found)    | API 或路由不存在。       |
| 200/20000 (OK)     | 成功。               |

### post `api/login`

>   使用 `email` 和 `password` 账号登录并获取 `access_token` 。如果 token 失效请重新请求此登录接口 ，但请注意：不要在旧 token(s) 没有失效的情况下，频繁调用本接口。

#### 请求示例

```bash
curl -X POST http://127.0.0.1:18080/api/login -H "Content-Type: application/json" --data '{"email":"foo@example.com", "password":"123456"}'
```

#### 响应示例

使用 `20000` 状态码表示成功。

```json
{
    "code": "20000",
    "message": "ok",
    "data": {
        "uid": 1,
        "expired_at": 1555047338,
        "token": "eroQJLGjRfwM8kEY1DjCIQTGkDCgwgcV4t4P0WfsM5",
        "created_at": "2019-04-12 12:35:38",
        "updated_at": "2019-04-12 12:35:38"
    },
    "timestamp": "2019-04-12 12:35:38"
}
```

当失败或者错误时，请使用 `非2xx` （如 `403` 、`500`  等）数字状态。

```json
{
    "code": "403",
    "message": "illegal or incorrect credentials",
    "data": {},
    "timestamp": "2019-04-12 12:35:38"
}
```

### get `api/pages`

>   获取当前用户所有页面，附带分页。

#### 请求示例

```
curl http://127.0.0.1:18080/api/pages -H "Content-Type: application/json" -H "AUTHORIZATION: Bearer TVC66rtXnv7pw3jge4EtyC7qtyKKPxjjGyVUi4K2D"

# with page
curl http://127.0.0.1:18080/api/pages?page=2&per_page=2 -H "Content-Type: application/json" -H "AUTHORIZATION: Bearer TVC66rtXnv7pw3jge4EtyC7qtyKKPxjjGyVUi4K2D"
```

#### 响应示例

```json
{
    "code": "20000",
    "message": "ok",
    "data": {
        "total": 2,
        "per_page": 2,
        "current_page": 1,
        "items": [
            {
                "id": "1",
                "content": "# Hello world\n\nThis is a demo page.",
                "created_at": "2017-11-09 13:54:39",
                "updated_at": "2017-11-09 13:54:39"
            }
        ]
    },
    "timestamp": "2019-04-12 12:35:38"
}
```

### post `api/page`

>   创建一个新页面。

#### 请求示例

```bash
# POST raw data (in `json` format)
curl -X POST http://127.0.0.1:18080/api/page --data '{"content":"# Hello world\n\nThis is another demo page."}' -H "Content-Type: application/json" -H "AUTHORIZATION: Bearer TVC66rtXnv7pw3jge4EtyC7qtyKKPxjjGyVUi4K2D"
```

#### 响应示例

```json
{
    "code": "20000",
    "message": "ok",
    "data": {},
    "timestamp": "2019-04-12 12:35:38"
}
```

### get `api/page/@id`

>   获取指定 id 页面。

#### 请求示例

```bash
curl http://127.0.0.1:18080/api/page/4 -H "Content-Type: application/json" -H "AUTHORIZATION: Bearer TVC66rtXnv7pw3jge4EtyC7qtyKKPxjjGyVUi4K2D"
```

#### 响应示例

```json
{
    "code": "20000",
    "message": "ok",
    "data": {
        "id": "4",
        "uid": "1",
        "content": "# Hello world\n\nThis is another demo page.",
        "created_at": "2017-11-09 20:36:52",
        "updated_at": "2017-11-09 20:36:52"
    },
    "timestamp": "2019-04-12 12:35:38"
}
```

### put `api/page/@id`

>   更新指定 id 页面。

#### 请求示例

```bash
# PUT raw data (in `json` format)
curl -X PUT http://127.0.0.1:18080/api/page/4 --data '{"content":"# Demo\n\nThis is another demo page."}' -H "Content-Type: application/json" -H "AUTHORIZATION: Bearer TVC66rtXnv7pw3jge4EtyC7qtyKKPxjjGyVUi4K2D"
```

#### 响应示例

```json
{
    "code": "20000",
    "message": "ok",
    "data": {},
    "timestamp": "2019-04-12 12:35:38"
}
```

### delete `/api/page/@id`

>   删除指定 id 页面。

#### 请求示例

```bash
# DELETE
curl -X DELETE http://127.0.0.1:18080/api/page/4 -H "Content-Type: application/json" -H "AUTHORIZATION: Bearer TVC66rtXnv7pw3jge4EtyC7qtyKKPxjjGyVUi4K2D"
```

#### 响应示例

```json
{
    "code": "20000",
    "message": "ok",
    "data": {},
    "timestamp": "2019-04-12 12:35:38"
}
```
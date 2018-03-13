--ip请求限制   web:limit:ip:127.0.0.1
--user请求限制   web:limit:user:abc
local limit_key = KEYS[1]..':'..KEYS[2]..KEYS[3]
local limitCount = tonumber(ARGV[1])
local expire_time = ARGV[2]

--是否存在
local is_exists = redis.call("EXISTS", limit_key)
if is_exists == 1 then
        --大于限制数返回失败，
    if tonumber(redis.call("GET", limit_key)) >= limitCount then
        return -1
    else
        --不大于则加1返回成功
        redis.call("INCR", limit_key)
        return 1
    end
else
    --没有，则限制数初始化1并且缓存且设置过期时间
    redis.call("SET", limit_key, 1)
    redis.call("EXPIRE", limit_key, expire_time)
    return 1
end
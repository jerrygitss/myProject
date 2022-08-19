local key = KEYS[1] --限流KEY（一秒一个）
local limit = tonumber(ARGV[1]) --限流大小
local duration = ARGV[2] --过期时间，秒



local current = tonumber(redis.call('get', key) or "0")
local total = tonumber(redis.call('get', "total") or "0")
if current + 1 > limit or total +1 > 5 then
    --如果超出限流大小
    return 0
else
    --请求数+1，并设置2秒过期
    redis.call("INCRBY", key, "1")
    redis.call("expire", key, duration)

    redis.call("INCRBY", "total", "1")
    redis.call("expire", "total", duration)
    return 1
end
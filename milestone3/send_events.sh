cat <<HERE > /tmp/test.json
{"charging":"250"}
{"charging":"8880"}
HERE

curl -X POST "http://localhost:8080/device/13579"\
     -H "Content-Type: multipart/form-data"\
     -F "file=@/tmp/test.json"\

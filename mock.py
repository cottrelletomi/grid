import requests
import random
import time

users = ["toto@toto.com", "jean@toto.com", "tom@toto.com", "marie@toto.com", "sophie@toto.com"]
index = 0
baseHr = 140

def randomHr(hr) :
    hrR = hr + random.randint(0,50)
    print(hrR)
    return hrR

while True:
    print(users[index])

    data = {
        "email": users[index],
        "heart_rate": randomHr(baseHr)
    }

    response = requests.post("http://172.31.250.105:3001/api/v1/sensor/hr", json=data)

    if(index == 4) :
        index = 0
    else :
        index = index + 1

    print('CODE RETOUR : ',response.status_code)
    print('==================================')

    time.sleep(1)
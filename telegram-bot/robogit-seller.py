import telebot
import requests
import json

BASE_HOST = "http://robogit.org:8080/api/telebot"

f = open(".teletoken", "r")
token = f.read().rstrip()
f.close()

bot = telebot.TeleBot(token)


@bot.message_handler(commands=['start', 'help'])
def send_help(message):
    resp = "Hello! I'm the official RoboGit shop assistant.\n" \
           "Here I am to help you make payment for your order\n" \
           "Here is the list of available commands:\n" \
           "/help - get this message\n" \
           "/pay [order_id] - pay for the order with id = [order_id]\n"
    bot.send_message(message.chat.id, resp)


@bot.message_handler(commands=['pay'])
def send_payment(message):
    if len(str(message.text).split()) < 2:
        bot.send_message(message.chat.id, "You should send command in the format:\n"
                                          "/pay [order_id], for example:\n"
                                          "/pay 1001\n"
                                          "Please, try again")
        return
    order_id = str(message.text).split()[1]
    tele_id = str(message.chat.id)
    print(tele_id)
    r = requests.get(BASE_HOST + f"/getOrder/{tele_id}/{order_id}")
    print(BASE_HOST + f"/getOrder/{tele_id}/{order_id}")
    response_json = r.content.decode("UTF-8")
    response_obj = json.loads(response_json)
    if str(response_obj["id"]) != order_id or response_obj["paid"]:
        bot.send_message(message.chat.id, "This order has been already paid")
        return
    r = requests.get(BASE_HOST + f"/getFullPrice/{order_id}")
    print(BASE_HOST + f"/getFullPrice/{order_id}")
    response_json = r.content.decode("UTF-8")
    response_obj = json.loads(response_json)
    if float(str(response_obj[0]["sum"])) <= 0.0000001:
        bot.send_message(message.chat.id, "This order is free (lol)")
        return
    bot.send_message(message.chat.id, "Click the link below to pay:\n"
                                      "https://www.blockchain.com/btc/payment_request?"
                                      "address=12droSfCah25BG22KR6pNPVf612DXD8CZH&"
                                      f"amount={round(float(str(response_obj[0]['sum'])), 7)}&"
                                      f"message=RoboGit%20order%20{order_id}")


@bot.message_handler(commands=['setAddress'])
def set_address(message):
    order_id = str(message.text).split()[1]
    tele_id = str(message.chat.id)
    address = str(message.text).split(' ', 2)[2]
    r = requests.post(BASE_HOST + "/setDestination",
                      json={"orderId": int(order_id), "telegramId": tele_id, "address": address})
    if r.status_code == 200:
        bot.send_message(message.chat.id, f"Destination address for order {order_id} set")
    else:
        bot.send_message(message.chat.id, f"Your order with id: {order_id} was not found")


if __name__ == '__main__':
    print("Bot has been started!")
    bot.polling(none_stop=True)

import ast
import telebot
import requests
import json

f = open(".teletoken", "r")
token = f.read().rstrip()
f.close()

bot = telebot.TeleBot(token)


# @bot.message_handler(content_types=['text'])
# def main_controller(message):
#     send_help(message)


@bot.message_handler(commands=['start', 'help'])
def send_help(message):
    resp = "Hello! I'm the official RoboGit shop assistant.\n" \
           "Here I am to help you make payment for your order\n" \
           "Here is the list of available commands:\n" \
           "/help - get this message\n" \
           "/pay [order_id] - pay for the order with id = [order_id]\n"
    bot.send_message(message.chat.id, resp)


BASE_HOST = "http://robogit.org:8080/api/telebot"


@bot.message_handler(commands=['pay'])
def send_payment(message):
    # r = requests.get(BASE_HOST + f"/getOrder/{message.chat.id}/{message}")  # FIXME
    order_id = str(message.text).split()[1]
    tele_id = "12324141"  # FIXME
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
                                      f"amount={str(response_obj[0]['sum'])}&"
                                      f"message=RoboGit%20order%20{order_id}")


if __name__ == '__main__':
    print("Bot has been started!")
    bot.polling(none_stop=True)

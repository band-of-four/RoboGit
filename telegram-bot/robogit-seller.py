import telebot
import requests
import json
from datetime import datetime

BASE_HOST = "https://localhost:8443/api/telebot"

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
           "/pay [order_id] - pay for the order with id = [order_id]\n" \
           "/setAddress [order_id] [address] - set delivery address for the order with id = [order_id]\n" \
           "/setDeliveryDate [order_id] [date] - set delivery date for the order with id = [order_id]\n" \
           "\t[date] must be in the format: the DD.MM.YYYY, for example: 23.01.2019"
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

    r = requests.get(BASE_HOST + f"/getOrder/{tele_id}/{order_id}", verify=False)
    response = json.loads(r.content.decode("UTF-8"))
    print(response)

    if str(response["id"]) != order_id or response["paid"]:
        bot.send_message(message.chat.id, "This order has been already paid")
        return

    r = requests.get(BASE_HOST + f"/getFullPrice/{order_id}", verify=False)
    response = json.loads(r.content.decode("UTF-8"))
    if float(str(response[0]["sum"])) <= 0.0000001:
        bot.send_message(message.chat.id, "This order is free (lol)")
        return
    bot.send_message(message.chat.id, "Click the link below to pay:\n"
                                      "https://www.blockchain.com/btc/payment_request?"
                                      "address=12droSfCah25BG22KR6pNPVf612DXD8CZH&"
                                      f"amount={round(float(str(response[0]['sum'])), 7)}&"
                                      f"message=RoboGit%20order%20{order_id}")


@bot.message_handler(commands=['setAddress', 'setaddress'])
def set_address(message):
    if len(str(message.text).split()) < 3:
        bot.send_message(message.chat.id, "You should send command in the format:\n"
                                          "/setAddress [order_id] [address], for example:\n"
                                          "/setAddress 1001 Spb, Vyazma 5/9\n"
                                          "Please, try again")
        return
    order_id = str(message.text).split()[1]
    tele_id = str(message.chat.id)
    address = str(message.text).split(' ', 2)[2]

    if not order_id.isdigit():
        bot.send_message(message.chat.id, f"Order must be a number\nPlease, try again")
        return
    elif len(address) < 3:
        bot.send_message(message.chat.id, f"Too short address\nPlease, try again")
        return

    r = requests.post(BASE_HOST + "/setDestination",
                      json={"orderId": int(order_id), "telegramId": str(tele_id), "address": str(address)}, verify=False)
    if r.status_code == 200:
        bot.send_message(message.chat.id, f"Destination address for order {order_id} set")
    else:
        bot.send_message(message.chat.id, f"Your order with id: {order_id} was not found")


def is_valid_date(date_text):
    try:
        datetime.strptime(date_text, '%d.%m.%Y')
        return True
    except ValueError:
        return False


@bot.message_handler(commands=['setDeliveryDate', 'setdeliverydate'])
def set_delivery_date(message):
    if len(str(message.text).split()) < 3:
        bot.send_message(message.chat.id, "You should send command in the format:\n"
                                          "/setDeliveryDate [order_id] [date], for example:\n"
                                          "/setDeliveryDate 1001 22.01.2019\n"
                                          "Please, try again")
        return
    order_id = str(message.text).split()[1]
    tele_id = str(message.chat.id)
    date_str = str(message.text).split(' ', 2)[2]

    if not order_id.isdigit():
        bot.send_message(message.chat.id, f"Order must be a number\nPlease, try again")
        return
    elif not is_valid_date(date_str):
        bot.send_message(message.chat.id, f"Incorrect date format, should be DD.MM.YYYY")
        return

    delivery_date = datetime.strptime(date_str, '%d.%m.%Y')
    r = requests.post(BASE_HOST + "/setDeliveryDate",
                      json={"orderId": int(order_id),
                            "telegramId": str(tele_id),
                            "date": delivery_date.strftime('%Y-%m-%d')}, verify=False)
    response = r.content.decode("UTF-8")
    bot.send_message(message.chat.id, f"{response}")


if __name__ == '__main__':
    print("Bot has been started!")
    bot.polling(none_stop=True)

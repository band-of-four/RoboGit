import telebot
import requests

f = open(".teletoken", "r")
token = f.read().rstrip()
f.close()

bot = telebot.TeleBot(token)


@bot.message_handler(content_types=['text'])
def main_controller(message):
    send_help(message)


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
    r = requests.post()
    bot.send_message(message.chat.id, "Link to the payment here")


if __name__ == '__main__':
    print("Bot has been started!")
    bot.polling(none_stop=True)

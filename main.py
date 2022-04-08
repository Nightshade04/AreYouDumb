import random
from kivy.app import App
from kivy.uix.label import Label
from kivy.uix.floatlayout import FloatLayout
from kivy.uix.anchorlayout import AnchorLayout
from kivy.uix.button import Button
from kivy.core.window import Window

Window.size = (500, 300)


class Container(FloatLayout):
    def __init__(self, **kwargs):
        super(Container, self).__init__(**kwargs)

        self.counter = 0
        self.yes_messages = ['Knew it... LOL.  :3',
                             'See, that wasn\'t so hard to accept... :3',
                             'If you say so! XD']
        self.no_messages = ['What are you doing? You know you are dumb... Accept it.',
                            'Damn dude, you persistent! Just accept that you are dumb',
                            'There ain\'t no use fighting it. You. Are. Dumb. Accept it.',
                            'Are you dumb? If not, try closing me!!!']

        anchor_label = AnchorLayout(anchor_x='center', anchor_y='top')
        self.message = Label(text='Are you dumb? If not, try closing me!!!', size=(100, 100), size_hint=(None, None))
        anchor_label.add_widget(self.message)
        self.add_widget(anchor_label)

        anchor_yes_btn = AnchorLayout(anchor_x='left', anchor_y='center')
        yes_button = Button(text="Yes", size=(100, 100), size_hint=(.4, .2))
        yes_button.bind(on_press=self.yesButtonPress)
        anchor_yes_btn.add_widget(yes_button)
        self.add_widget(anchor_yes_btn)

        self.anchor_no_btn = FloatLayout(size=(300, 300))
        no_button = Button(text="No", size_hint=(.4, .2),
                           pos=(20, 20))
        no_button.bind(on_press=self.noButtonPress)
        self.anchor_no_btn.add_widget(no_button)
        self.add_widget(self.anchor_no_btn)

    def yesButtonPress(self, instance):
        self.message.text = self.getDifferentMessage(self.yes_messages)
        self.remove_widget(self.anchor_no_btn)

    def noButtonPress(self, instance):
        instance.pos = (random.randint(0, 400), random.randint(0, 200))
        self.counter = self.counter + 1
        if self.counter % 5 == 0:
            self.message.text = self.getDifferentMessage(self.no_messages)

    def getDifferentMessage(self, message_repo):
        while True:
            message = message_repo[random.randint(0, len(message_repo) - 1)]
            if message != self.message.text:
                break
        return message


class AreYouDumb(App):
    def build(self):
        parent = Container()

        return parent


if __name__ == '__main__':
    AreYouDumb().run()

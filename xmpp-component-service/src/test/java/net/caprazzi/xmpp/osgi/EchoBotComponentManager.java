package net.caprazzi.xmpp.osgi;

import net.caprazzi.xmpp.component.*;
import net.caprazzi.xmpp.component.bot.PacketProcessor;
import net.caprazzi.xmpp.component.bot.ResponsePacket;
import net.caprazzi.xmpp.component.bot.BotResponses;
import net.caprazzi.xmpp.component.bot.BotServiceManager;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;

public class EchoBotComponentManager extends AbstractBotManager {

    public static void main(String[] args) {
        new EchoBotComponentManager().run(args);
    }

    @Override
    protected void initialize(BotServiceManager botService) {
        botService.addDomain("foo", "secret");

        botService.addBot(new PacketProcessor() {
            @Override
            public ResponsePacket processPacket(Packet packet) {
                if (packet instanceof Message) {
                    Message reply = new Message();
                    reply.setTo(packet.getFrom());
                    reply.setFrom(packet.getTo());
                    reply.setBody("You said: " + ((Message)packet).getBody());
                    return BotResponses.from(reply);
                }
                return BotResponses.none();
            }
        }, "foo", NodeFilters.singleNode("echo"));
    }
}

package wtf.taksa.mixin.network;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.ClientLoginPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.engine.events.controllers.EventType;
import wtf.taksa.engine.events.storage.NetworkEvents;

@Mixin(ClientConnection.class)
public class MixinClientConnection {
    @Inject(at = @At("HEAD"), method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V", cancellable = true)
    public void channelRead0Pre(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
        NetworkEvents.Receive e = NetworkEvents.Receive.obtain(EventType.Pre, packet);
        try {
            e.call();
            if (e.isCancelled()) {
                ci.cancel();
            }
        } finally {
            e.release();
        }
    }

    @Inject(at = @At("RETURN"), method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V", cancellable = true)
    public void channelRead0Post(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
        NetworkEvents.Receive e = NetworkEvents.Receive.obtain(EventType.Post, packet);
        try {
            e.call();
            if (e.isCancelled()) {
                ci.cancel();
            }
        } finally {
            e.release();
        }
    }

    @Inject(at = @At("HEAD"), method = "send(Lnet/minecraft/network/packet/Packet;)V", cancellable = true)
    public void sendPre(Packet<?> packet, CallbackInfo ci) {
        NetworkEvents.Send e = NetworkEvents.Send.obtain(EventType.Pre, packet);
        try {
            e.call();
            if (e.isCancelled()) {
                ci.cancel();
            }
        } finally {
            e.release();
        }
    }

    @Inject(at = @At("RETURN"), method = "send(Lnet/minecraft/network/packet/Packet;)V", cancellable = true)
    public void sendPost(Packet<?> packet, CallbackInfo ci) {
        NetworkEvents.Send e = NetworkEvents.Send.obtain(EventType.Post, packet);
        try {
            e.call();
            if (e.isCancelled()) {
                ci.cancel();
            }
        } finally {
            e.release();
        }
    }

    @Inject(at = @At("HEAD"), method = "connect(Ljava/lang/String;ILnet/minecraft/network/listener/ClientLoginPacketListener;)V", cancellable = true)
    public void connectPre(String address, int port, ClientLoginPacketListener listener, CallbackInfo ci) {
        NetworkEvents.Join e = NetworkEvents.Join.obtain(EventType.Pre, address, port);
        try {
            e.call();
            if (e.isCancelled()) {
                ci.cancel();
            }
        } finally {
            e.release();
        }
    }

    @Inject(at = @At("RETURN"), method = "connect(Ljava/lang/String;ILnet/minecraft/network/listener/ClientLoginPacketListener;)V", cancellable = true)
    public void connectPost(String address, int port, ClientLoginPacketListener listener, CallbackInfo ci) {
        NetworkEvents.Join e = NetworkEvents.Join.obtain(EventType.Post, address, port);
        try {
            e.call();
            if (e.isCancelled()) {
                ci.cancel();
            }
        } finally {
            e.release();
        }
    }

    @Inject(at = @At("HEAD"), method = "disconnect(Lnet/minecraft/text/Text;)V", cancellable = true)
    public void disconnectPre(Text reason, CallbackInfo ci) {
        NetworkEvents.Disconnect e = NetworkEvents.Disconnect.obtain(EventType.Pre, reason.getString());
        try {
            e.call();
            if (e.isCancelled()) {
                ci.cancel();
            }
        } finally {
            e.release();
        }
    }

    @Inject(at = @At("RETURN"), method = "disconnect(Lnet/minecraft/text/Text;)V", cancellable = true)
    public void disconnectPost(Text reason, CallbackInfo ci) {
        NetworkEvents.Disconnect e = NetworkEvents.Disconnect.obtain(EventType.Post, reason.getString());
        try {
            e.call();
            if (e.isCancelled()) {
                ci.cancel();
            }
        } finally {
            e.release();
        }
    }
}
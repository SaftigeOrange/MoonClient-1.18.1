package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundSetBeaconPacket implements Packet<ServerGamePacketListener>
{
    private final int primary;
    private final int secondary;

    public ServerboundSetBeaconPacket(int pPrimary, int pSecondary)
    {
        this.primary = pPrimary;
        this.secondary = pSecondary;
    }

    public ServerboundSetBeaconPacket(FriendlyByteBuf pBuffer)
    {
        this.primary = pBuffer.readVarInt();
        this.secondary = pBuffer.readVarInt();
    }

    public void write(FriendlyByteBuf pBuffer)
    {
        pBuffer.writeVarInt(this.primary);
        pBuffer.writeVarInt(this.secondary);
    }

    public void handle(ServerGamePacketListener pHandler)
    {
        pHandler.handleSetBeaconPacket(this);
    }

    public int getPrimary()
    {
        return this.primary;
    }

    public int getSecondary()
    {
        return this.secondary;
    }
}

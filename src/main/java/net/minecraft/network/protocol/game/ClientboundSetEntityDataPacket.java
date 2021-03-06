package net.minecraft.network.protocol.game;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.SynchedEntityData;

public class ClientboundSetEntityDataPacket implements Packet<ClientGamePacketListener>
{
    private final int id;
    @Nullable
    private final List < SynchedEntityData.DataItem<? >> packedItems;

    public ClientboundSetEntityDataPacket(int pId, SynchedEntityData pEntityData, boolean pSendAll)
    {
        this.id = pId;

        if (pSendAll)
        {
            this.packedItems = pEntityData.getAll();
            pEntityData.clearDirty();
        }
        else
        {
            this.packedItems = pEntityData.packDirty();
        }
    }

    public ClientboundSetEntityDataPacket(FriendlyByteBuf p_179290_)
    {
        this.id = p_179290_.readVarInt();
        this.packedItems = SynchedEntityData.unpack(p_179290_);
    }

    public void write(FriendlyByteBuf pBuffer)
    {
        pBuffer.writeVarInt(this.id);
        SynchedEntityData.pack(this.packedItems, pBuffer);
    }

    public void handle(ClientGamePacketListener pHandler)
    {
        pHandler.handleSetEntityData(this);
    }

    @Nullable
    public List < SynchedEntityData.DataItem<? >> getUnpackedData()
    {
        return this.packedItems;
    }

    public int getId()
    {
        return this.id;
    }
}

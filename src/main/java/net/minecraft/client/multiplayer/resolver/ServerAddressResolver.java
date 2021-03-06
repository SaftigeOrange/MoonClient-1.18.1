package net.minecraft.client.multiplayer.resolver;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@FunctionalInterface
public interface ServerAddressResolver
{
    Logger LOGGER = LogManager.getLogger();
    ServerAddressResolver SYSTEM = (p_171878_) ->
    {
        try {
            InetAddress inetaddress = InetAddress.getByName(p_171878_.getHost());
            return Optional.of(ResolvedServerAddress.from(new InetSocketAddress(inetaddress, p_171878_.getPort())));
        }
        catch (UnknownHostException unknownhostexception)
        {
            LOGGER.debug("Couldn't resolve server {} address", p_171878_.getHost(), unknownhostexception);
            return Optional.empty();
        }
    };

    Optional<ResolvedServerAddress> resolve(ServerAddress pServerAddress);
}

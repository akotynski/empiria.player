package eu.ydp.empiria.player.client.module.dragdrop;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class SourcelistManagerModel {

    private Map<String, SourcelistGroup> groups = Maps.newHashMap();
    private Map<String, String> clientIdToSourcelistIdCache = Maps.newHashMap();

    public void registerClient(SourcelistClient client) {
        String sourcelistId = client.getSourcelistId();
        if (!Strings.isNullOrEmpty(sourcelistId)) {
            SourcelistGroup group = getOrCreateSourcelistGroup(sourcelistId);
            group.addClient(client);
            clientIdToSourcelistIdCache.put(client.getIdentifier(), sourcelistId);
        }
    }

    public void registerSourcelist(Sourcelist sourcelist) {
        String sourceListId = sourcelist.getIdentifier();
        SourcelistGroup group = getOrCreateSourcelistGroup(sourceListId);
        group.setSourcelist(sourcelist);
    }

    public Set<Sourcelist> getSourceLists() {
        return FluentIterable.from(groups.values()).transform(sourcelistsFromGroups).toSet();
    }

    public Collection<SourcelistClient> getClients(Sourcelist sourcelist) {
        String sourcelistId = sourcelist.getIdentifier();
        return groups.get(sourcelistId).getClients();
    }

    public SourcelistClient getClientById(String clientId) {
        String sourcelistId = clientIdToSourcelistIdCache.get(clientId);
        SourcelistGroup group = groups.get(sourcelistId);
        Optional<SourcelistClient> optionalClient = group.getClientById(clientId);
        return optionalClient.orNull();
    }

    public Sourcelist getSourcelistByClientId(String clientId) {
        String sourcelistId = clientIdToSourcelistIdCache.get(clientId);
        return groups.get(sourcelistId).getSourcelist();
    }

    public boolean containsClient(String clientId) {
        return clientIdToSourcelistIdCache.containsKey(clientId);
    }

    public Sourcelist getSourcelistById(String sourcelistId) {
        return groups.get(sourcelistId).getSourcelist();
    }

    public void lockGroup(Sourcelist sourcelist) {
        String sourcelistId = sourcelist.getIdentifier();
        groups.get(sourcelistId).setLocked(true);
    }

    public void unlockGroup(Sourcelist sourcelist) {
        String sourcelistId = sourcelist.getIdentifier();
        groups.get(sourcelistId).setLocked(false);
    }

    public boolean isGroupLocked(Sourcelist sourcelist) {
        String sourcelistId = sourcelist.getIdentifier();
        return groups.get(sourcelistId).isLocked();
    }

    private SourcelistGroup getOrCreateSourcelistGroup(String sourceListId) {
        if (!groups.containsKey(sourceListId)) {
            SourcelistGroup group = new SourcelistGroup();
            groups.put(sourceListId, group);
        }
        return groups.get(sourceListId);
    }

    private final Function<SourcelistGroup, Sourcelist> sourcelistsFromGroups = new Function<SourcelistGroup, Sourcelist>() {

        @Override
        public Sourcelist apply(SourcelistGroup group) {
            return group.getSourcelist();
        }
    };
}

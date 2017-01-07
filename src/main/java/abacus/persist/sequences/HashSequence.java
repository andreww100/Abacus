package abacus.persist.sequences;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.internal.databaseaccess.Accessor;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.sequencing.Sequence;
import org.eclipse.persistence.sessions.Session;

import java.util.UUID;
import java.util.Vector;

/**
 */
public class HashSequence extends Sequence implements SessionCustomizer {

    public HashSequence() {
        super();
    }

    public HashSequence(String name) {
        super(name);
    }

    @Override
    public void customize(Session session) throws Exception {
        UUIDSequence sequence = new UUIDSequence("system-hash");

        session.getLogin().addSequence(sequence);
    }

    @Override
    public boolean shouldAcquireValueAfterInsert() {
        return false;
    }

    @Override
    public boolean shouldUseTransaction() {
        return true;
    }

    @Override
    public Object getGeneratedValue(Accessor accessor,
                                    AbstractSession writeSession, String seqName) {
        return UUID.randomUUID().toString().toUpperCase();
    }

    @Override
    public Vector getGeneratedVector(Accessor accessor,
                                     AbstractSession writeSession, String seqName, int size) {
        return null;
    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onDisconnect() {

    }
}

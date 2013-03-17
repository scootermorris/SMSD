/*
 * Copyright (C) 2013 Syed Asad Rahman <asad at ebi.ac.uk>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package org.openscience.smsd.mcss;

import java.io.Serializable;
import java.util.BitSet;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.fingerprint.ShortestPathFingerprinter;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesGenerator;

/**
 * @cdk.module smsd
 * @cdk.githash
 * @author Syed Asad Rahman <asad@ebi.ac.uk>
 *
 */
final public class Fragment implements Comparable<Fragment>, Serializable {

    private static final long serialVersionUID = 134634654886765L;

    public synchronized IAtomContainer getContainer() {
        return container;
    }
    private final BitSet fingerprint;
    private final long fingerprintAsLong;
    private final IAtomContainer container;

    public Fragment(IAtomContainer container) throws CDKException {
        if (container == null) {
            throw new CDKException("NULL container not supported");
        }
        this.container = container;
        this.fingerprint = new ShortestPathFingerprinter().getBitFingerprint(container).asBitSet();
        this.fingerprintAsLong = convert(this.fingerprint);
    }

    @Override
    public synchronized boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fragment other = (Fragment) obj;

        if (this.getContainer() != other.getContainer() && (this.getContainer() == null
                || (this.getContainer().getAtomCount() != other.getContainer().getAtomCount()))) {
            return false;
        }

        if (this.getFingerprint() != other.getFingerprint() && (this.getFingerprint() == null
                || !this.getFingerprint().equals(other.getFingerprint()))) {
            return false;
        }
        if (this.fingerprintAsLong != other.fingerprintAsLong) {
            return false;
        }

        return true;
    }

    @Override
    public synchronized int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.getFingerprint() != null ? this.getFingerprint().hashCode() : 0);
        hash = 47 * hash + (int) (this.fingerprintAsLong ^ (this.fingerprintAsLong >>> 32));
        return hash;
    }

    @Override
    public synchronized int compareTo(Fragment t) {

        if (this.fingerprintAsLong == t.fingerprintAsLong) {
            return 0;
        } else if (this.fingerprintAsLong > t.fingerprintAsLong) {
            return 1;
        } else {
            return -1;
        }
    }

    private synchronized long convert(BitSet bits) {
        long value = 0L;
        if (bits == null || bits.isEmpty()) {
            return value;
        }
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1L << i) : 0L;
        }
        return value;
    }

    /**
     * @return the fingerprint
     */
    public BitSet getFingerprint() {
        return fingerprint;
    }

    /**
     * Return SMILES
     *
     * @param ac
     * @return
     */
    public static String toSmiles(IAtomContainer ac) {
        SmilesGenerator g = new SmilesGenerator();
        g.setUseAromaticityFlag(true);
        return g.createSMILES(ac);
    }
}

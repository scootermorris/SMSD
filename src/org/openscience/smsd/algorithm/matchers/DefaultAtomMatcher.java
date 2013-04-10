/* Copyright (C) 2009-2013  Syed Asad Rahman <asad@ebi.ac.uk>
 *
 * Contact: cdk-devel@lists.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 */
package org.openscience.smsd.algorithm.matchers;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.annotations.TestClass;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.isomorphism.matchers.IQueryAtom;

/**
 * Checks if atom is matching between query and target molecules.
 *
 * @cdk.module smsd
 * @cdk.githash
 *
 * @author Syed Asad Rahman <asad@ebi.ac.uk>
 */
@TestClass("org.openscience.cdk.smsd.algorithm.vflib.VFLibTest")
public final class DefaultAtomMatcher implements AtomMatcher {

    private final String SMALLEST_RING_SIZE = "SMALLEST_RING_SIZE";
    static final long serialVersionUID = -7861469841127327812L;
    private final String symbol;
    private final IAtom qAtom;
    private final boolean shouldMatchRings;

    /**
     * Constructor
     *
     * @param qAtom
     * @param symbol
     * @param shouldMatchRings
     */
    public DefaultAtomMatcher(IAtom qAtom,
            String symbol,
            boolean shouldMatchRings) {
        this.qAtom = qAtom;
        this.symbol = symbol;
        this.shouldMatchRings = shouldMatchRings;
    }

    /**
     * Constructor
     *
     * @param atom query atom
     * @param shouldMatchRings ring matching flag
     */
    public DefaultAtomMatcher(IAtom atom, boolean shouldMatchRings) {
        this(atom, atom.getSymbol(), shouldMatchRings);
    }

    private boolean matchSymbol(IAtom atom) {
        if (getAtomSymbol() == null) {
            return false;
        }
        return getAtomSymbol().equals(atom.getSymbol());
    }

    /**
     * {@inheritDoc}
     *
     * @param targetAtom
     * @return
     */
    @Override
    public boolean matches(IAtom targetAtom) {
        if (targetAtom instanceof IQueryAtom) {
            return ((IQueryAtom) targetAtom).matches(getQueryAtom());
        } else if (getQueryAtom() != null && getQueryAtom() instanceof IQueryAtom) {
            return ((IQueryAtom) getQueryAtom()).matches(targetAtom);
        } else {
            if (!matchSymbol(targetAtom)) {
                return false;
            }

            if (isShouldMatchRings()
                    && isAtomAttachedToRing(getQueryAtom())
                    && isAtomAttachedToRing(targetAtom)) {
                return true;
            } else if (isShouldMatchRings()
                    && (isAliphaticAtom(getQueryAtom()) && isRingAtom(targetAtom))) {
                return false;
            } else if (isShouldMatchRings()
                    && (isRingAtom(getQueryAtom()) && isAliphaticAtom(targetAtom))) {
                return false;
            } /*
             * This tiggers error in matching example C00026_C00217
             */ //            else if (shouldMatchRings
            //                    && !(isRingAtom(qAtom) && isRingAtom(targetAtom))
            //                    && qAtom.getHybridization() != null
            //                    && targetAtom.getHybridization() != null
            //                    && !qAtom.getHybridization().equals(targetAtom.getHybridization())) {
            //                return false;
            //            } 
            else if (isShouldMatchRings() && (isRingAtom(getQueryAtom()) && isRingAtom(targetAtom))) {
                if (getQueryAtom().getProperty(SMALLEST_RING_SIZE) != targetAtom.getProperty(SMALLEST_RING_SIZE)) {
                    return false;
                }
            }

        }
        return true;
    }

    private boolean isAliphaticAtom(IAtom atom) {
        return atom.getFlag(CDKConstants.ISALIPHATIC) ? true : false;
    }

    private boolean isRingAtom(IAtom atom) {
        return atom.getFlag(CDKConstants.ISINRING) ? true : false;
    }

    private boolean isAtomAttachedToRing(IAtom atom) {
        return ((Integer) atom.getProperty(CDKConstants.RING_CONNECTIONS)).intValue() > 0 ? true : false;
    }

    /**
     * @return the qAtom
     */
    @Override
    public IAtom getQueryAtom() {
        return qAtom;
    }

    /**
     * @return the symbol
     */
    public String getAtomSymbol() {
        return symbol;
    }

    /**
     * @return the shouldMatchRings
     */
    public boolean isShouldMatchRings() {
        return shouldMatchRings;
    }
}

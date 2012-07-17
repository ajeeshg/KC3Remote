package org.kuali.kra.award.paymentreports.specialapproval.foreigntravel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.kuali.kra.award.AwardForm;
import org.kuali.kra.award.contacts.AwardPerson;
import org.kuali.kra.award.home.Award;
import org.kuali.kra.bo.Contactable;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.core.util.KeyLabelPair;

/**
 *
 */
public class ApprovedForeignTravelerValuesFinder extends KeyValuesBase {
    /**
     * Default c'tor
     */
    public ApprovedForeignTravelerValuesFinder() {

    }

    // @Override - bug in JDK 5 fixed in JDK 6. See http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6399361
    public List<KeyLabelPair> getKeyValues() {
        Award award = getAward();
        List<KeyLabelPair> list = new ArrayList<KeyLabelPair>();
        Set<String> listNames = new TreeSet<String>();
        for(AwardPerson person: award.getProjectPersons()) {
            Contactable contact = person.getContact();
            addEligibleTravelerToList(list, listNames, contact);
        }
        for(AwardApprovedForeignTravel trip: award.getApprovedForeignTravelTrips()) {
            Contactable contact = trip.getTraveler();
            addEligibleTravelerToList(list, listNames, contact);
        }
        return list;
    }

    protected Award getAward() {
        return ((AwardForm) GlobalVariables.getKualiForm()).getAwardDocument().getAward();
    }

    private void addEligibleTravelerToList(List<KeyLabelPair> list, Set<String> listNames, Contactable contact) {
        if(contact != null) {
            String fullName = contact.getFullName();
            if(!listNames.contains(fullName)) {
                list.add(new KeyLabelPair(contact.getIdentifier().toString(), fullName));
                listNames.add(fullName);
            }
        }
    }
}

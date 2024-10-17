package de.innocept.ocpp.feature.profile;

/*
   ChargeTime.eu - Java-OCA-OCPP

   MIT License

   Copyright (C) 2016-2018 Thomas Volden <tv@chargetime.eu>
   Copyright (C) 2018 Mikhail Kladkevich <kladmv@ecp-share.com>
   Copyright (C) 2019 Kevin Raddatz <kevin.raddatz@valtech-mobility.com>

   Permission is hereby granted, free of charge, to any person obtaining a copy
   of this software and associated documentation files (the "Software"), to deal
   in the Software without restriction, including without limitation the rights
   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
   copies of the Software, and to permit persons to whom the Software is
   furnished to do so, subject to the following conditions:

   The above copyright notice and this permission notice shall be included in all
   copies or substantial portions of the Software.

   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
   SOFTWARE.
*/

import de.innocept.ocpp.feature.Feature;
import de.innocept.ocpp.feature.ProfileFeature;
import de.innocept.ocpp.model.Confirmation;
import de.innocept.ocpp.model.Request;
import de.innocept.ocpp.feature.CancelReservationFeature;
import de.innocept.ocpp.feature.ReserveNowFeature;
import de.innocept.ocpp.model.reservation.CancelReservationRequest;
import de.innocept.ocpp.model.reservation.ReserveNowRequest;
import java.util.HashSet;
import java.util.UUID;

public class ClientReservationProfile implements Profile {

  private HashSet<Feature> features;
  private ClientReservationEventHandler eventHandler;

  public ClientReservationProfile(ClientReservationEventHandler eventHandler) {
    this.eventHandler = eventHandler;
    features = new HashSet<>();
    features.add(new CancelReservationFeature(this));
    features.add(new ReserveNowFeature(this));
  }

  @Override
  public ProfileFeature[] getFeatureList() {
    return features.toArray(new ProfileFeature[0]);
  }

  @Override
  public Confirmation handleRequest(UUID sessionIndex, Request request) {
    Confirmation result = null;

    if (request instanceof CancelReservationRequest) {
      result = eventHandler.handleCancelReservationRequest((CancelReservationRequest) request);
    } else if (request instanceof ReserveNowRequest) {
      result = eventHandler.handleReserveNowRequest((ReserveNowRequest) request);
    }

    return result;
  }
}

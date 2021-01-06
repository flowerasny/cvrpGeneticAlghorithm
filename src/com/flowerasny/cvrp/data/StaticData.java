package com.flowerasny.cvrp.data;

import com.flowerasny.cvrp.model.Client;
import com.flowerasny.cvrp.model.StartingPoint;
import com.flowerasny.cvrp.model.Vehicle;

import java.util.List;

public class StaticData {

    private final static StartingPoint startingPoint = new StartingPoint("Kraków", 50.0466814, 19.8647903);

    public final static List<Client> clients = List.of(
            new Client("Białystok", 53.1276046, 23.0858554, 500),
            new Client("Bielsko-Biała", 49.8121789, 18.9670518, 50),
            new Client("Chrzanów", 50.1287598, 19.2884881, 400),
            new Client("Gdańsk", 54.3610059, 18.5496047, 200),
            new Client("Gdynia", 54.5038045, 18.3932687, 100),
            new Client("Gliwice", 50.3012185, 18.3932687, 40),
            new Client("Gromnik", 49.8390228, 20.9450616, 200),
            new Client("Katowice", 50.2136512, 18.9369822, 300),
            new Client("Kielce", 50.8540189, 20.5454309, 30),
            new Client("Krosno", 49.6896495, 21.7166586, 60),
            new Client("Krynica", 49.4154478, 20.898955, 50),
            new Client("Lublin", 51.218088, 22.4935597, 60),
            new Client("Łódź", 51.7730343, 19.3401699, 160),
            new Client("Malbork", 54.0286482, 19.0084415, 100),
            new Client("Nowy Targ", 49.4892464, 19.9737208, 120),
            new Client("Olsztyn", 53.7759903, 20.3956593, 300),
            new Client("Poznań", 52.4004458, 16.7615834, 100),
            new Client("Puławy", 51.4254995, 21.9046281, 200),
            new Client("Radom", 51.4150447, 21.0839343, 100),
            new Client("Rzeszów", 50.0054089, 21.9184153, 60),
            new Client("Sandomierz", 50.6780941, 21.675572, 200),
            new Client("Szczecin", 53.4296143, 14.4845414, 150),
            new Client("Szczucin", 50.3096329, 21.0617624, 60),
            new Client("Szklarska Poręba", 50.8142546, 15.3965229, 50),
            new Client("Tarnów", 50.0261227, 20.9068665, 70),
            new Client("Warszawa", 52.232855, 20.9211117, 200),
            new Client("Wieliczka", 49.9875567, 20.0286001, 90),
            new Client("Wrocław", 51.1269942, 16.8517813, 40),
            new Client("Zakopane", 49.27587, 19.9036652, 200),
            new Client("Zamość", 50.7213772, 23.2134076, 300)
    );

    public static List<Vehicle> getVehicles() {
        return List.of(
                new Vehicle("Star", 1000, startingPoint),
                new Vehicle("Mercedes", 1000, startingPoint),
                new Vehicle("Volvo", 1000, startingPoint),
                new Vehicle("Daf", 1000, startingPoint),
                new Vehicle("Iveco", 1000, startingPoint)
        );
    }
}

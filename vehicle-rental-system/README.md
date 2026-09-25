# DriveRent — Vehicle Rental System

A small Java project built for a Software Design Patterns assignment.
It is a vehicle rental system where a customer can choose a vehicle
(Car, Motorcycle or SUV) and a rental plan (Daily, Weekly or Monthly),
and the system calculates and displays the rental price.

The project demonstrates two structural design patterns:

- **Adapter Pattern** — used to integrate three vehicle providers that
  each expose a different, incompatible method for getting vehicle data.
- **Bridge Pattern** — used to separate the rental plan (Daily/Weekly/Monthly)
  from the vehicle type (Car/Motorcycle/SUV), so both can change independently.

---

## 1. How to run the project

**Requirements:** Java 11 or newer (JDK).

From the project root folder (the one that contains `src` and `web`):

```bash
# 1) Compile
find src -name "*.java" > sources.txt
javac -d out @sources.txt

# 2) Run (must be run from the project root, so it can find the "web" folder)
java -cp out Main
```

Then open your browser at:

```
http://localhost:8080
```

Pick a vehicle, choose a rental plan, enter a duration and your name,
click **Calculate Rental** to see the summary, then **Confirm Rental**
to confirm it.

---

## 2. Project structure

```
vehicle-rental-system/
├── src/
│   ├── Main.java                     Application entry point
│   ├── adapter/                      ADAPTER PATTERN
│   │   ├── Vehicle.java                  Target interface
│   │   ├── CarRentalProvider.java        Adaptee (car provider)
│   │   ├── MotorcycleRentalProvider.java Adaptee (motorcycle provider)
│   │   ├── SUVRentalProvider.java        Adaptee (SUV provider)
│   │   ├── CarAdapter.java               Adapter
│   │   ├── MotorcycleAdapter.java        Adapter
│   │   └── SUVAdapter.java               Adapter
│   ├── bridge/                       BRIDGE PATTERN
│   │   ├── VehicleImplementor.java       Implementation interface
│   │   ├── CarImplementor.java           Concrete implementation
│   │   ├── MotorcycleImplementor.java    Concrete implementation
│   │   ├── SUVImplementor.java           Concrete implementation
│   │   ├── RentalPlan.java               Abstraction
│   │   ├── DailyRental.java              Refined abstraction
│   │   ├── WeeklyRental.java             Refined abstraction
│   │   └── MonthlyRental.java            Refined abstraction
│   ├── model/
│   │   └── RentalResult.java         Simple data holder for the summary
│   ├── service/
│   │   └── RentalService.java        Connects Adapter + Bridge, business logic
│   └── controller/
│       └── RentalServer.java         HTTP server, serves the frontend and the API
└── web/                              FRONTEND
    ├── index.html
    ├── style.css
    └── script.js
```

---

## 3. Adapter Pattern

### Problem it solves

In a real rental company, vehicle information often comes from different,
already-existing systems (different providers per vehicle type), each with
its own method names and data formats. The rest of the application should
not need to know about those differences.

- `CarRentalProvider` exposes `getCar()`
- `MotorcycleRentalProvider` exposes `getMotorcycle()`
- `SUVRentalProvider` exposes `getSUV()`

These three methods are incompatible with each other: different names,
different return types, different field names. The Adapter Pattern lets
the rest of the system talk to all of them the same way.

### Roles in this project

| Role      | Class(es)                                                          |
|-----------|---------------------------------------------------------------------|
| Target    | `adapter.Vehicle` (interface with `getVehicleName()`, `getBrand()`, `getCategory()`) |
| Adaptee   | `CarRentalProvider`, `MotorcycleRentalProvider`, `SUVRentalProvider` |
| Adapter   | `CarAdapter`, `MotorcycleAdapter`, `SUVAdapter`                     |
| Client    | `service.RentalService`                                             |

### Text diagram

```
                 +----------------+
  Client  -----> |    Vehicle     |   (Target interface)
(RentalService)  |  getVehicleName()
                 |  getBrand()     |
                 |  getCategory()  |
                 +--------+-------+
                          ^
        +-----------------+------------------+
        |                 |                  |
  CarAdapter        MotorcycleAdapter    SUVAdapter      (Adapters)
        |                 |                  |
CarRentalProvider  MotorcycleRentalProvider SUVRentalProvider  (Adaptees)
   getCar()          getMotorcycle()          getSUV()
```

`RentalService` only ever calls `vehicle.getVehicleName()`,
`vehicle.getBrand()` and `vehicle.getCategory()`. It never calls
`getCar()`, `getMotorcycle()` or `getSUV()` directly — that translation
happens inside the adapters.

---

## 4. Bridge Pattern

### Problem it solves

The rental modality (Daily, Weekly, Monthly) and the vehicle type
(Car, Motorcycle, SUV) can be combined in many ways (Daily+Car,
Weekly+SUV, Monthly+Motorcycle, etc). If we created one class per
combination (`DailyCarRental`, `WeeklyCarRental`, `DailySUVRental`...)
we would end up with a class explosion. The Bridge Pattern avoids this
by splitting the two dimensions into separate hierarchies connected
through composition.

### Roles in this project

| Role                  | Class(es)                                             |
|------------------------|--------------------------------------------------------|
| Abstraction            | `bridge.RentalPlan`                                    |
| Refined Abstraction    | `DailyRental`, `WeeklyRental`, `MonthlyRental`          |
| Implementation         | `bridge.VehicleImplementor`                             |
| Concrete Implementation| `CarImplementor`, `MotorcycleImplementor`, `SUVImplementor` |

### Text diagram

```
   RentalPlan (Abstraction)  -------- has-a -------->  VehicleImplementor (Implementation)
        ^                                                        ^
        |                                                        |
  +-----+------+-------------+                     +-------------+--------------+
  |            |              |                     |              |            |
DailyRental WeeklyRental MonthlyRental        CarImplementor MotorcycleImplementor SUVImplementor
```

`RentalPlan` holds a reference to a `VehicleImplementor` (composition),
so any rental plan can be combined with any vehicle type at runtime:

```java
RentalPlan plan = new WeeklyRental(new SUVImplementor());
double total = plan.calculateTotalPrice(2); // Weekly + SUV, 2 weeks
```

Changing the rental plan does not require touching the vehicle classes,
and adding a new vehicle type does not require touching the rental plan
classes — the two hierarchies vary independently, which is exactly what
the Bridge Pattern is for.

---

## 5. Where the two patterns meet

`service/RentalService.java` is the class that ties everything together:

1. It uses the **Adapter Pattern** to get the vehicle's display
   information (name, brand, category) through the common `Vehicle`
   interface — no matter which provider it came from.
2. It uses the **Bridge Pattern** to build a `RentalPlan` (Daily/Weekly/
   Monthly) connected to a `VehicleImplementor` (Car/Motorcycle/SUV) and
   calculate the price.
3. It packages everything into a `RentalResult`, which
   `controller/RentalServer.java` turns into JSON for the frontend.

---

## 6. How to explain the project to the professor

**1. What the system does.**
DriveRent is a small vehicle rental website. The customer picks a
vehicle, a rental plan and a duration, and the system shows a price
summary and lets the customer confirm the rental.

**2. Why Adapter was necessary.**
Because the three vehicle providers (`CarRentalProvider`,
`MotorcycleRentalProvider`, `SUVRentalProvider`) each expose a different,
incompatible method for getting vehicle data. Without the Adapter
Pattern, the rest of the code would need an `if/else` for every provider.
With it, the code only depends on one interface: `Vehicle`.

**3. Why Bridge was necessary.**
Because the rental plan and the vehicle type are two independent things
that can be combined freely (Daily+Car, Weekly+SUV, Monthly+Motorcycle,
etc). Instead of creating one class per combination, Bridge separates
them into two hierarchies (`RentalPlan` and `VehicleImplementor`) that
are connected only through composition, so each one can grow on its own.

**4. Where Adapter appears in the code.**
In the `adapter` package: `Vehicle` is the target interface, the three
`...RentalProvider` classes are the incompatible adaptees, and
`CarAdapter`, `MotorcycleAdapter`, `SUVAdapter` are the adapters that
make them all look like a `Vehicle`.

**5. Where Bridge appears in the code.**
In the `bridge` package: `RentalPlan` is the abstraction,
`DailyRental`/`WeeklyRental`/`MonthlyRental` are the refined
abstractions, `VehicleImplementor` is the implementation interface, and
`CarImplementor`/`MotorcycleImplementor`/`SUVImplementor` are the
concrete implementations.

**6. How the frontend interacts with the patterns.**
The frontend (`web/index.html`, `style.css`, `script.js`) does not know
about the patterns at all — it only sends the selected vehicle, plan,
duration and customer name to `/api/calculate`. The Java backend
(`RentalServer` → `RentalService`) is where the Adapter and Bridge
patterns actually run, and the result is sent back as JSON to be
displayed in the result card.

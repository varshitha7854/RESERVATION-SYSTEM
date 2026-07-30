//package com.example.reservations_app.service;
//
//import com.example.reservations_app.entity.SeatEntity;
//import com.example.reservations_app.entity.TrainEntity;
//import com.example.reservations_app.model.ACSeat;
//import com.example.reservations_app.model.Seat;
//import com.example.reservations_app.model.SeatClass;
//import com.example.reservations_app.model.SleeperSeat;
//import com.example.reservations_app.model.Train;
//import com.example.reservations_app.repository.SeatRepository;
//import com.example.reservations_app.repository.TrainRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.locks.ReentrantLock;
//
//@Service
//public class TrainService {
//
//    private final TrainRepository trainRepository;
//    private final SeatRepository seatRepository;
//    private final Map<String, ReentrantLock> trainLocks = new ConcurrentHashMap<>();
//
//    public TrainService(TrainRepository trainRepository, SeatRepository seatRepository) {
//        this.trainRepository = trainRepository;
//        this.seatRepository = seatRepository;
//    }
//
//    public List<Train> findAll() {
//        return trainRepository.findAll().stream()
//                .map(this::toModel)
//                .sorted(Comparator.comparing(Train::getTrainNumber))
//                .toList();
//    }
//
//    public Optional<Train> findByNumber(String trainNumber) {
//        return trainRepository.findAll().stream()
//                .filter(train -> train.getTrainNumber().equalsIgnoreCase(trainNumber))
//                .map(this::toModel)
//                .findFirst();
//    }
//
//    public Optional<Train> findByNumberAndDate(String trainNumber, LocalDate journeyDate) {
//        return trainRepository.findByTrainNumberIgnoreCaseAndJourneyDate(trainNumber, journeyDate)
//                .map(this::toModel);
//    }
//
//    public List<Train> searchTrains(String source, String destination, LocalDate journeyDate) {
//        return trainRepository.findBySourceIgnoreCaseAndDestinationIgnoreCaseAndJourneyDateOrderByTrainNumberAsc(
//                        source,
//                        destination,
//                        journeyDate
//                ).stream()
//                .map(this::toModel)
//                .toList();
//    }
//
//    @Transactional
//    public Train saveTrain(Train train) {
//        validateTrain(train);
//        TrainEntity entity = trainRepository
//                .findByTrainNumberIgnoreCaseAndJourneyDate(train.getTrainNumber(), train.getJourneyDate())
//                .orElseGet(TrainEntity::new);
//        copyToEntity(train, entity);
//        return toModel(trainRepository.save(entity));
//    }
//
//    @Transactional
//    public Seat allocateSeat(String trainNumber, LocalDate journeyDate, SeatClass seatClass) {
//        ReentrantLock lock = lockFor(trainNumber, journeyDate);
//        lock.lock();
//        try {
//            TrainEntity train = getTrainEntityOrThrow(trainNumber, journeyDate);
//            SeatEntity seat = train.getSeats().stream()
//                    .filter(candidate -> !Boolean.TRUE.equals(candidate.getBooked()))
//                    .filter(candidate -> SeatClass.fromValue(candidate.getSeatClass()) == seatClass)
//                    .findFirst()
//                    .orElseThrow(() -> new IllegalArgumentException("No available seat for class: " + seatClass));
//            seat.setBooked(true);
//            return toSeatModel(seatRepository.save(seat));
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    @Transactional
//    public Seat reserveSeat(String trainNumber, LocalDate journeyDate, String seatNumber) {
//        ReentrantLock lock = lockFor(trainNumber, journeyDate);
//        lock.lock();
//        try {
//            TrainEntity train = getTrainEntityOrThrow(trainNumber, journeyDate);
//            SeatEntity seat = seatRepository.findByTrainAndSeatNumberIgnoreCase(train, seatNumber)
//                    .orElseThrow(() -> new IllegalArgumentException("Seat not found: " + seatNumber));
//
//            if (Boolean.TRUE.equals(seat.getBooked())) {
//                throw new IllegalArgumentException("This seat is already booked.");
//            }
//
//            seat.setBooked(true);
//            return toSeatModel(seatRepository.save(seat));
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    @Transactional
//    public void releaseSeat(String trainNumber, LocalDate journeyDate, String seatNumber) {
//        ReentrantLock lock = lockFor(trainNumber, journeyDate);
//        lock.lock();
//        try {
//            trainRepository.findByTrainNumberIgnoreCaseAndJourneyDate(trainNumber, journeyDate)
//                    .flatMap(train -> seatRepository.findByTrainAndSeatNumberIgnoreCase(train, seatNumber))
//                    .ifPresent(seat -> {
//                        seat.setBooked(false);
//                        seatRepository.save(seat);
//                    });
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    private TrainEntity getTrainEntityOrThrow(String trainNumber, LocalDate journeyDate) {
//        return trainRepository.findByTrainNumberIgnoreCaseAndJourneyDate(trainNumber, journeyDate)
//                .orElseThrow(() -> new IllegalArgumentException("Train not found: " + trainNumber));
//    }
//
//    private ReentrantLock lockFor(String trainNumber, LocalDate journeyDate) {
//        return trainLocks.computeIfAbsent(key(trainNumber, journeyDate), ignored -> new ReentrantLock(true));
//    }
//
//    private String key(String trainNumber, LocalDate journeyDate) {
//        return trainNumber.trim().toUpperCase() + "|" + journeyDate;
//    }
//
//    private void validateTrain(Train train) {
//        if (isBlank(train.getTrainNumber())
//                || isBlank(train.getTrainName())
//                || isBlank(train.getSource())
//                || isBlank(train.getDestination())
//                || train.getJourneyDate() == null
//                || train.getSeats() == null
//                || train.getSeats().isEmpty()) {
//            throw new IllegalArgumentException("Train number, name, route, date, and seats are required.");
//        }
//    }
//
//    private boolean isBlank(String value) {
//        return value == null || value.trim().isEmpty();
//    }
//
//    private Train toModel(TrainEntity entity) {
//        return new Train(
//                entity.getTrainNumber(),
//                entity.getTrainName(),
//                entity.getSource(),
//                entity.getDestination(),
//                entity.getJourneyDate(),
//                entity.getSeats().stream()
//                        .sorted(Comparator
//                                .comparing((SeatEntity seat) -> SeatClass.fromValue(seat.getSeatClass()).ordinal())
//                                .thenComparingInt(this::seatIndex)
//                                .thenComparing(SeatEntity::getSeatNumber))
//                        .map(this::toSeatModel)
//                        .toList()
//        );
//    }
//
//    private Seat toSeatModel(SeatEntity entity) {
//        SeatClass seatClass = SeatClass.fromValue(entity.getSeatClass());
//        Seat seat = seatClass.isAC()
//                ? new ACSeat(entity.getSeatNumber(), seatClass)
//                : new SleeperSeat(entity.getSeatNumber());
//        seat.setBooked(Boolean.TRUE.equals(entity.getBooked()));
//        return seat;
//    }
//
//    private void copyToEntity(Train train, TrainEntity entity) {
//        entity.setTrainNumber(train.getTrainNumber());
//        entity.setTrainName(train.getTrainName());
//        entity.setSource(train.getSource());
//        entity.setDestination(train.getDestination());
//        entity.setJourneyDate(train.getJourneyDate());
//        entity.getSeats().clear();
//        train.getSeats().forEach(seat -> entity.addSeat(toEntity(seat)));
//    }
//
//    private SeatEntity toEntity(Seat seat) {
//        SeatEntity entity = new SeatEntity();
//        entity.setSeatNumber(seat.getSeatNumber());
//        entity.setSeatClass(seat.getSeatClass().name());
//        entity.setBaseFare(seat.getBaseFare());
//        entity.setBooked(seat.isBooked());
//        return entity;
//    }
//
//    private int seatIndex(SeatEntity seat) {
//        String seatNumber = seat.getSeatNumber();
//        String numericPart = seatNumber.contains("-")
//                ? seatNumber.substring(seatNumber.lastIndexOf('-') + 1)
//                : seatNumber.replaceAll("\\D", "");
//        try {
//            return Integer.parseInt(numericPart);
//        } catch (NumberFormatException exception) {
//            return Integer.MAX_VALUE;
//        }
//    }
//}

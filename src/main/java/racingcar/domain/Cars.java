package racingcar.domain;

import racingcar.utils.RandomNumberGenerator;
import racingcar.view.RacingCarError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Cars {
    private final List<Car> carList;

    public Cars(List<String> carNameList) {
        checkOverlappedNames(carNameList);
        checkNull(carNameList);
        this.carList = carNameList.stream()
                .map(Car::new)
                .collect(Collectors.toList());
    }

    private void checkOverlappedNames(List<String> nameCandidates) {
        Set<String> targetSet = new HashSet<>(nameCandidates);
        if (nameCandidates.size() != targetSet.size()) {
            RacingCarError.overlapped();
        }
    }

    private void checkNull(List<String> nameCandidates) {
        if (nameCandidates.size() == 0) {
            RacingCarError.nullCar();
        }
    }

    private int findMaxPosition() {
        int maxPosition = 0;
        for (Car car : this.carList) {
            maxPosition = Math.max(maxPosition, car.getPosition());
        }
        return maxPosition;
    }

    public void processOneTurn() {
        carList.forEach(car -> car.oneTurn(RandomNumberGenerator.turnNumber()));
    }

    public List<Car> getList() {
        return this.carList;
    }

    public String findWinners() {
        int maxPosition = findMaxPosition();
        List<String> winnerList = new ArrayList<>();
        carList.stream()
                .filter(carElement -> (carElement.isMaxPosition(maxPosition)))
                .map(Car::getName)
                .forEach(winnerList::add);
        return String.join(", ", winnerList);
    }
}
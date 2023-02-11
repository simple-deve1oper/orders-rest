package dev.orders.service;

import dev.orders.entity.Unit;
import dev.orders.exception.EntityExistsException;
import dev.orders.exception.EntityNotFoundException;
import dev.orders.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UnitService {
    @Autowired
    private UnitRepository unitRepository;

    /**
     * Поиск списка всех объектов типа Unit
     * @return список объектов типа Unit
     */
    public List<Unit> findAllUnits() {
        return unitRepository.findAll();
    }

    /**
     * Получение объекта типа Unit по сокращённому наименованию
     * @param shortName - сокращённое наименование
     * @return объект типа Unit
     */
    public Unit findByShortName(String shortName) {
        return unitRepository.findByShortName(shortName)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Единица измерения '%s' не найдена", shortName)));
    }

    /**
     * Сохранение объекта типа Unit
     * @param unit - объект типа Unit
     * @return сохранённый объект типа Unit
     */
    @Transactional
    public Unit saveUnit(Unit unit) {
        if (unitRepository.findByShortName(unit.getShortName()).isPresent()) {
            throw new EntityExistsException(String.format("Единица измерения с таким наименованием уже существует", unit.getShortName()));
        }

        return unitRepository.save(unit);
    }

    /**
     * Удаление объекта типа Product по сокращённому наименованию
     * @param shortName - сокращённое наименование
     */
    @Transactional
    public void deleteByShortName(String shortName) {
        if (unitRepository.findByShortName(shortName).isEmpty()) {
            throw new EntityNotFoundException(String.format("Единица измерения '%s' не найдена", shortName));
        }

        unitRepository.deleteByShortName(shortName);
    }
}

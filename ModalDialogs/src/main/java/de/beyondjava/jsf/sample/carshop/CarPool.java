/**
 *  (C) 2013-2014 Stephan Rauh http://www.beyondjava.net
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.beyondjava.jsf.sample.carshop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CarPool implements Serializable {
	private static final long serialVersionUID = 1L;

	private final static int SIZE_OF_INITIAL_CAR_POOL = 10;

	@ManagedProperty("#{staticOptionBean}")
	private StaticOptionBean staticOptions;

	public StaticOptionBean getStaticOptions() {
		return staticOptions;
	}

	public void setStaticOptions(StaticOptionBean staticOptions) {
		this.staticOptions = staticOptions;
	}

	public DynamicOptionBean getDynamicOptions() {
		return dynamicOptions;
	}

	public void setDynamicOptions(DynamicOptionBean dynamicOptions) {
		this.dynamicOptions = dynamicOptions;
	}

	@ManagedProperty("#{dynamicOptionBean}")
	private DynamicOptionBean dynamicOptions;

	private List<String> types;

	private int currentYear = Calendar.getInstance().get(Calendar.YEAR);

	private List<Car> carPool;

	public List<Car> getCarPool() {
		return carPool;
	}

	private List<Car> selectedCars;

	public List<Car> getSelectedCars() {
		return selectedCars;
	}

	/** This method is also used as actionListener */
	@PostConstruct
	public void initRandomCarPool() {
		types = dynamicOptions.getTypesToBrand(null);
		carPool = new ArrayList<Car>();
		for (int i = 0; i < SIZE_OF_INITIAL_CAR_POOL; i++) {
			carPool.add(getRandomCar());
		}
		selectedCars = carPool;
	}

	public void setCarPool(List<Car> carpool) {
		this.carPool = carpool;
	}

	private Car getRandomCar() {
		int typeIndex = (int) Math.floor(Math.random() * (types.size() - 1));
		String type = types.get(typeIndex + 1);
		String brand = dynamicOptions.getBrandToType(type);
		int year = (int) (Math.floor((currentYear - 1980) * Math.random())) + 1980;
		int age = currentYear - year;

		int price = 60000 / (1 + age) + (int) Math.floor(Math.random() * 10000);

		int mileage = (int) (Math.floor((age + 1) * 20000 * Math.random()));

		int colorIndex = (int) Math.floor(Math.random() * (staticOptions.getColors().size() - 1));
		String color = staticOptions.getColors().get(colorIndex + 1);

		int fuelIndex = (int) Math.floor(Math.random() * (staticOptions.getFuels().size() - 1));
		String fuel = staticOptions.getFuels().get(fuelIndex + 1);

		Car c = new Car(brand, type, year, color, mileage, fuel, price);
		return c;
	}

}

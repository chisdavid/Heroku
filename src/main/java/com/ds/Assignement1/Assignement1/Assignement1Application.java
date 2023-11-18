package com.ds.Assignement1.Assignement1;

import com.ds.Assignement1.Assignement1.Enums.UserType;
import com.ds.Assignement1.Assignement1.Model.*;
import com.ds.Assignement1.Assignement1.Repository.DeviceRepository;
import com.ds.Assignement1.Assignement1.Repository.PeopleRepository;
import com.ds.Assignement1.Assignement1.Repository.RoleRepository;
import com.ds.Assignement1.Assignement1.Repository.SensorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ds.Assignement1.Assignement1.Utils.Utils.passwordEncoder;

@SpringBootApplication
public class Assignement1Application {

	public static void main(String[] args) {
		SpringApplication.run(Assignement1Application.class, args);
	}
	@Bean
	CommandLineRunner init(RoleRepository roleRepository, PeopleRepository peopleRepository, SensorRepository sensorRepository, DeviceRepository deviceRepository) {
		return args -> {

			Sensor sensor = Sensor.builder().description("Descrierer 1").maxValue(120L).name("Senzor 1").build();
			sensorRepository.save(sensor);

			Sensor sensor1 = Sensor.builder().description("Descriere 2").maxValue(100L).name("Senzor 2").build();
			sensorRepository.save(sensor1);

			Device device = Device.builder()
					.address("Cluj Napoca nr 4")
					.name("Device 1")
					.description("O descriere foarte buna")
					.averageEnergyConsumption(100L)
					.maximumEnergyConsumption(200L)
					.sensor(sensor)
					.build();

			Device device1 = Device.builder()
					.address("Cluj ")
					.name("Device 2")
					.description("O descriere  buna")
					.averageEnergyConsumption(100L)
					.maximumEnergyConsumption(200L)
					.sensor(sensor1)
					.build();

			deviceRepository.save(device1);
			deviceRepository.save(device);

			List<Device> devices1 = new ArrayList<Device>();
			devices1.add(device1);


			List<Device> devices = new ArrayList<Device>();
			devices.add(device);

			Role role = Role.builder()
					.status(new ArrayList<Status>())
					.userType(UserType.Admin)
					.password(passwordEncoder().encode( "1234"))
					.username("david")
					.build();
			roleRepository.save(role);

			Person person = Person.builder()
					.role(role)
					.address("Cluj Napoca nr 4")
					.name("Chis David adrian")
					.phoneNumber("+40744807415")
					.birthDate(new Date())
					.email("chis_david_27@yahoo.com")
					.build();

			Role roleNatan = Role.builder()
					.userType(UserType.Client)
					.username("natan")
					.password(passwordEncoder().encode("1234"))
					.build();
			Role rolePerson = Role.builder()
					.username("user")
					.password(passwordEncoder().encode("1234"))
					.userType(UserType.Client)
					.build();
			roleRepository.save(rolePerson);

			roleRepository.save(roleNatan);
			Person natan = Person.builder()
					.devices(devices)
					.role(roleNatan)
					.name("Gavrea Ioan")
					.address("Cluj Napoca, Strada Constanta nr 4")
					.phoneNumber("+40744807415")
					.birthDate(new Date())
					.email("GavreaIoan@gmail.com")
					.build();

			Person pers2 = Person.builder()
					.devices(devices1)
					.role(rolePerson)
					.name("User 1")
					.address("Strada Partizanilor nr 4 Simleul Silvaniei")
					.phoneNumber("+4087533123")
					.birthDate(new Date())
					.email("dariadamaris@yahoo.com")
					.build();

			peopleRepository.save(pers2);
			peopleRepository.save(natan);

			peopleRepository.save(person);
		};
	}
}
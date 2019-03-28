window.onload = function() {
	console.log('app loading..');
	loadLandingView();
}

function loadLandingView() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		// here is where we manipulate our response.. whenever we get it
		if (xhr.readyState == 4) {
			console.log('response received');
			if (xhr.status == 200) {
				// success
				// we know that our response will be HTML
				$('#view').html(xhr.responseText);

				// ADD EVENT LISTENERS TO OUR HTML
				$('#submit').on('click', loginUser);
			}
		}
	}
	xhr.open("GET", "login.view");
	xhr.send();
}


function loginUser() {
	
	let username = $('#username').val();
	let password = $('#password').val();
	
	if(validateStrings(username) || validateStrings(password)){
	let xhr = new XMLHttpRequest();
	let toSend = [username, password];
	xhr.onreadystatechange = function(){
		if(xhr.readyState==4 && xhr.status==200){	
//			let name = JSON.parse(xhr.responseText);
			let user = xhr.responseText;
			console.log(xhr.getAllResponseHeaders());		
			if(name == "invalid")
				$('#message').html('<font color="red">Sorry! Invalid credentials!</font>');
			else	
				loadHomeView(user);
		}	
	}
	xhr.open("POST", "login");
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(JSON.stringify(toSend));
	}
	else
		$('#message').html('Please enter valid username and password!');
}

function loadHomeView(user){
	let userArray = user.split(" ");
	let firstName = userArray[0];
	let lastName = userArray[1];
	let role = userArray[2];
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.responseText){
			$('#view').html(xhr.responseText);
		}
	}
	console.log(role);
	let view;
	if (role.toLowerCase() == "fi_manager") {
		view = "manager.view";
		$('#name').val(firstName + " " + lastName);
	}	
	else {
		view = "employee.view";
		$('#name').val(firstName + " " + lastName);
	}
	xhr.open("GET", view);
	xhr.send();
}

function getReinbursements() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.responseText){
			$('#view').html(xhr.responseText);
//			$('#goToLogin').on('click', loadLoginView);
//			
//			//make sure that username is unique before allowing user to submit 
//			$('#username').on('blur', validateUser);
//			
//			//create new user 
//			$('#addUser').on('click', addUser);
		}
	}
	xhr.open("GET", "employee");
	xhr.send();
}

function validateStrings(str) {
	if(str == null || str == '') 
		return false;
	else 
		return true;
}
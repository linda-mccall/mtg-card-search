import { Text, View, StyleSheet } from 'react-native';
import * as React from 'react';
import { useNavigation } from '@react-navigation/native';

import { Link } from 'expo-router';

interface SignUpFormState  {
  username: string;
  email: string;
  password: string
}



export default function Index() {

  const [username, setUsername] = React.useState('')
  const [password, setPassword] = React.useState('')

  const testLoginCredentials = {
    username: "linda-fenton",
    email: "linda.fenton@unosquare.com",
    password: "password"
  }

  const navigation = useNavigation()

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    // Do something
    event.preventDefault()
    if (username === testLoginCredentials.username && password === testLoginCredentials.password) {
      window.localStorage.setItem("username", username)
      alert("Hi " + username)
    } else {
      alert("BAD PASSWORD! BOO! YOU SUCK! ")
    }
  };

  const handleUsernameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(event.target.value)
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value)
  };

  return (

    <View style={styles.container}>
      <form onSubmit={handleSubmit}>
          <div>
            <label style={styles.text} htmlFor="usernameInput">Username:</label>
            <input
              id="usernameInput"
              type="text"
              onChange={handleUsernameChange}
              value={username}
            />
          </div>
          <div>
            <label style={styles.text} htmlFor="passwordInput">Password:</label>
            <input
              id="passwordInput"
              type="text"
              onChange={handlePasswordChange}
              value={password}
            />
          </div>
          <button type="submit">Submit</button>
      </form>
      <br></br>
      <br></br>

      <Link href="/" style={styles.button}>
        Go to Home screen
      </Link>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#25292e',
    alignItems: 'center',
    justifyContent: 'center',
  },
  text: {
    color: '#fff',
  },
  button: {
    fontSize: 20,
    textDecorationLine: 'underline',
    color: '#fff',
  },
});
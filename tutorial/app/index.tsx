import { StyleSheet } from 'react-native';

import { View } from '@/components/Themed';
import { useNavigation, router } from 'expo-router';
import React from 'react';
import mockServer from '@/api/server';

export default function Index() {

  mockServer();

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
      router.replace("/home")
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
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
  },
  separator: {
    marginVertical: 30,
    height: 1,
    width: '80%',
  },
});


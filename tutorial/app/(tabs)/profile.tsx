import { StyleSheet } from 'react-native';

import { Text, View } from '@/components/Themed';

export default function Profile() {
  let userName: string | null = window.localStorage.getItem("username") ;

  if(userName == null) {
    userName = "Guest"
  }

  let welcomeMessage = `Hello ${userName}`

  return (
    <View style={styles.container}>
      <Text style={styles.title}>{welcomeMessage}</Text>
      <View style={styles.separator} lightColor="#eee" darkColor="rgba(255,255,255,0.1)" />
      <Text style={styles.title}>todo</Text>
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

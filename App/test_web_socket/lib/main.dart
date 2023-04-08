import 'package:flutter/material.dart';
import 'package:test_web_socket/home_controller.dart';

Future<void> main() async {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final HomeController homeController = HomeController();

  @override
  void initState() {
    super.initState();
    homeController.startListener();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: AnimatedBuilder(
          animation: homeController,
          builder: (context, widget) {
            return ListView.builder(
              itemCount: homeController.messsages.length,
              itemBuilder: (context, index) {
                return ListTile(
                  title: Text(homeController.messsages[index]),
                );
              },
            );
          }),
    );
  }
}

import 'package:flutter/cupertino.dart';
import 'package:web_socket_channel/web_socket_channel.dart';

class HomeController extends ChangeNotifier {
  List<String> messsages = [];

  void startListener() {
    final wsUrl = Uri.parse('ws://192.168.122.1:8080/my-websocket-endpoint?id=teste');
    var channel = WebSocketChannel.connect(wsUrl);
    channel.stream.listen((message) {
      addMessage(message);
    }).onError((error) {
      print("🔴 $error");
    });
  }

  void addMessage(String message) {
    messsages.add(message);
    notifyListeners();
  }
}

import 'package:flutter/material.dart';

class AppTheme {
  static final Color primaryColor = Colors.deepPurple;
  static final Color secondaryColor = Colors.deepPurpleAccent;

  static ThemeData get lightTheme {
    return ThemeData(
      primaryColor: primaryColor,
      colorScheme: ColorScheme.fromSeed(seedColor: primaryColor),
      appBarTheme: const AppBarTheme(
        backgroundColor: Colors.deepPurple,
        foregroundColor: Colors.white,
        elevation: 2,
        centerTitle: true,
      ),
      elevatedButtonTheme: ElevatedButtonThemeData(
        style: ElevatedButton.styleFrom(
          backgroundColor: primaryColor,
          foregroundColor: Colors.white,
          padding: const EdgeInsets.symmetric(vertical: 14, horizontal: 24),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(8),
          ),
          textStyle: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
        ),
      ),
      inputDecorationTheme: InputDecorationTheme(
        border: OutlineInputBorder(borderRadius: BorderRadius.circular(8)),
        focusedBorder: OutlineInputBorder(
          borderSide: BorderSide(color: primaryColor, width: 2),
          borderRadius: BorderRadius.circular(8),
        ),
        labelStyle: TextStyle(color: primaryColor),
      ),
      textTheme: const TextTheme(
        headline6: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
        bodyText2: TextStyle(fontSize: 16),
      ),
      scaffoldBackgroundColor: Colors.grey[50],
    );
  }
}

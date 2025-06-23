import 'package:flutter/material.dart';

class Breadcrumb extends StatelessWidget {
  final List<BreadcrumbItem> items;

  const Breadcrumb({Key? key, required this.items}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    List<Widget> widgets = [];

    for (var i = 0; i < items.length; i++) {
      final item = items[i];

      widgets.add(
        GestureDetector(
          onTap: item.onTap,
          child: Text(
            item.label,
            style: TextStyle(
              color: item.onTap != null ? Colors.blue : Colors.black,
              decoration: item.onTap != null ? TextDecoration.underline : null,
              fontWeight: i == items.length - 1 ? FontWeight.bold : FontWeight.normal,
            ),
          ),
        ),
      );

      if (i < items.length - 1) {
        widgets.add(const Padding(
          padding: EdgeInsets.symmetric(horizontal: 8),
          child: Text('>'),
        ));
      }
    }

    return Row(
      mainAxisSize: MainAxisSize.min,
      children: widgets,
    );
  }
}

class BreadcrumbItem {
  final String label;
  final VoidCallback? onTap;

  BreadcrumbItem({required this.label, this.onTap});
}

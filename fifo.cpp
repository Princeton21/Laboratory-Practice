#include <bits/stdc++.h>
using namespace std;
int main() {
	int f;
	cin >> f;
	vector<int> fifo(f, -1);
	int n;
	cin >> n;
	vector<int> input(n);
	for (int i = 0; i < n; ++i) cin >> input[i];

	cout << "-------------------------------";

	int hit = 0;
	int fault = 0;
	int j = 0;

	bool check;
	for (int i = 0; i < n; i++) {
		check = false;
		for (int k = 0; k < f; k++) {
			if (fifo[k] == input[i]) {
				check = true;
				hit++;
			}
		}
		if (check == false) {
			fifo[j] = input[i];
			j++;
			j %= f;
			fault++;
		}
	}
	float ratio;
	ratio = (double)hit / (double)n;
	cout << "\nHIT : " << hit << " FAULT : " << fault << " HIT RATIO : " << ratio << endl;

}
#include<bits/stdc++.h>
using namespace std;
int main() {
	int n;
	cout << " enter the number of processes";
	cin >> n;
	int i, pos = 0, temp;
	cout << "Enter the burst times : ";
	vector<int> p(n), bt(n), pt(n), wt(n), tat(n);

	for (i = 0; i < n; ++i) {p[i] = i + 1; cin >> bt[i];}

	cout << "Enter the priority item : ";
	for (i = 0; i < n; ++i) cin >> pt[i];

	for (i = 0; i < n; ++i) {
		pos = i;
		for (int j = i + 1; j < n; ++j) {
			if (pt[j] < pt[pos]) pos = j;
		}

		swap(pt[pos], pt[i]);
		swap(p[pos], p[i]);
		swap(bt[pos], bt[i]);
	}

	wt[0] = 0;

	for (i = 1; i < n; ++i) {
		wt[i] = wt[i - 1] + bt[i - 1];
	}
	cout << "Process\tBT\tpriority\twaiting time\tTAT\n";

	for (i = 0; i < n; ++i) {
		tat[i] = bt[i] + wt[i];
		cout << p[i] << " \t " << bt[i] << "\t" << pt[i] << "\t" << wt[i] << "\t" << tat[i] << endl;
	}
}
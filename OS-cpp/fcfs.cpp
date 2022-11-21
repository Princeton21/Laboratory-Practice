#include<bits/stdc++.h>
using namespace std;
int main() {
	cout << "Enter the number of processes : \n";
	int n;
	cin >> n;
	vector<int> at(n), bt(n), ct(n), tat(n), wt(n);
	int temp;
	double avgwt = 0, avgta = 0;

	cout << "Enter the arrival time for the processes : \n";
	for (int i = 1; i <= n; ++i) {
		cout << "P" << i << " : ";
		cin >> at[i - 1];
	}

	cout << "Enter the Burst time for the processes : \n";
	for (int i = 1; i <= n; ++i) {
		cout << "P" << i << " : ";
		cin >> bt[i - 1];
	}

	for (int i = 0; i < n; ++i) {
		if (i == 0) ct[0] = at[0] + bt[0];
		else {
			if (at[i] > ct[i - 1]) ct[i] = at[i] + bt[i];
			else ct[i] = ct[i - 1] + bt[i];
		}
		tat[i] = ct[i] - at[i];
		wt[i] = tat[i] - bt[i];
		avgwt += wt[i];
		avgta += tat[i];
	}
	cout << "\nSrno Arrival  Burst Completion Turnaround Waiting\n";
	for (int i = 0; i < n; ++i)
		cout << i + 1 << "\t \t" << at[i] << "\t \t" << bt[i] << "\t  \t" << ct[i] <<  "\t \t" << tat[i] << "\t     \t" << wt[i] << endl;
	cout << "\nAverage waiting time : " << avgwt / n << endl;
	cout << "\nAverage turnaround time : " << avgta / n << endl;
}